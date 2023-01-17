package com.app.kokonut.collectInformation;

import com.app.kokonut.activityHistory.ActivityHistoryService;
import com.app.kokonut.activityHistory.dto.ActivityCode;
import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.dtos.AdminCompanyInfoDto;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.collectInformation.dto.CollectInfoDetailDto;
import com.app.kokonut.collectInformation.dto.CollectInfoListDto;
import com.app.kokonut.collectInformation.dto.CollectInfoSearchDto;
import com.app.kokonut.collectInformation.entity.CollectInformation;
import com.app.kokonut.common.AjaxResponse;
import com.app.kokonut.common.ResponseErrorCode;
import com.app.kokonut.common.component.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : CollectInfomationService 개인정보 처리방침 - 개인정보 수집 및 이용 안내 서비스
 */
@Slf4j
@Service
public class CollectInformationService {
    private final CollectInformationRepository collectInfoRepository;
    private final AdminRepository adminRepository;
    private final ActivityHistoryService activityHistoryService;

    public CollectInformationService(CollectInformationRepository collectInfoRepository, AdminRepository adminRepository, ActivityHistoryService activityHistoryService) {
        this.collectInfoRepository = collectInfoRepository;
        this.adminRepository = adminRepository;
        this.activityHistoryService = activityHistoryService;
    }

    /**
     * 개인정보처리방침 목록 조회
     * @param userRole  사용자 권한
     * @param email     사용자 이메일
     * @param collectInfoSearchDto 검색조건
     * @param pageable 페이징 처리를 위한 정보
     */
    public ResponseEntity<Map<String, Object>> collectInfoList(String userRole, String email, CollectInfoSearchDto collectInfoSearchDto, Pageable pageable) {
        log.info("collectInfoList 호출, userRole : " + userRole);

        AjaxResponse res = new AjaxResponse();

        // 접속정보에서 필요한 정보 가져오기.
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : " + email));

        Integer companyIdx = admin.getCompanyIdx();
        // TODO 메뉴 권한에 대한 체크는 프론트 단에서 진행하기로 함.
        if (!"[MASTER]".equals(userRole) && !"[ADMIN]".equals(userRole)) {
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
        } else {
            log.info("개인정보 수집 및 이용 안내 목록 조회");
            Page<CollectInfoListDto> collectInfoListDtos = collectInfoRepository.findCollectInfoPage(companyIdx, collectInfoSearchDto, pageable);
            return ResponseEntity.ok(res.ResponseEntityPage(collectInfoListDtos));
        }
    }

    /**
     * 개인정보처리방침 조회
     * @param userRole  사용자 권한
     * @param idx       개인정보처리방침 인덱스
     */
    public ResponseEntity<Map<String,Object>> collectInfoDetail(String userRole, Integer idx) {
        log.info("collectInfoDetail 호출, userRole : " + userRole);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        // TODO 메뉴 권한에 대한 체크 부분은 프론트 단에서 진행하기로 함.
        if(!"[MASTER]".equals(userRole) && !"[ADMIN]".equals(userRole)){
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
        }else{
            if(idx == null){
                log.error("idx 값을 확인 할 수 없습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO051.getCode(), ResponseErrorCode.KO051.getDesc()));
            }else{
                log.info("개인정보 수집 및 이용 안내 상세 조회, idx : " + idx);
                CollectInfoDetailDto collectInfoDetailDto = collectInfoRepository.findCollectInfoByIdx(idx);
                if(collectInfoDetailDto != null){
                    // 조회 성공
                    log.info("개인정보 수집 및 이용 안내 상세 조회 성공, idx : " + collectInfoDetailDto.getIdx() + ", title : " + collectInfoDetailDto.getTitle());
                    data.put("collectInfoDetailDto",  collectInfoDetailDto);
                    return ResponseEntity.ok(res.success(data));
                }else{
                    // 조회 실패
                    log.error("개인정보 수집 및 이용 안내 상세 조회 실패, idx : " +idx);
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO052.getCode(), ResponseErrorCode.KO052.getDesc()));
                }
            }
        }
    }

    /**
     * 개인정보처리방침 저장, 수정
     * @param userRole  사용자 권한
     * @param email     사용자 이메일
     * @param collectInfoDetailDto 개인정보처리방침 내용
     */
    @Transactional
    public ResponseEntity<Map<String, Object>> collectInfoSave(String userRole, String email, CollectInfoDetailDto collectInfoDetailDto) {
        log.info("collectInfoSave 호출, userRole : " + userRole);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        // 접속정보에서 필요한 정보 가져오기.
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : " + email));
        AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

        if (adminCompanyInfoDto == null) {
            log.error("회사 정보가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "회사 정보가 " + ResponseErrorCode.KO004.getDesc()));
        } else {
            // TODO 메뉴 권한에 대한 체크 부분은 프론트 단에서 진행하기로 함.
            if(!"[MASTER]".equals(userRole) && !"[ADMIN]".equals(userRole)){
                log.error("접근권한이 없습니다. userRole : " + userRole);
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
            }else{
                CollectInformation saveCollectInfo = new CollectInformation();
                Integer adminIdx = admin.getIdx();
                String adminName = admin.getName();

                ActivityCode activityCode;
                Integer companyIdx = adminCompanyInfoDto.getCompanyIdx();
                String ip = CommonUtil.clientIp();
                String businessNumber = adminCompanyInfoDto.getBusinessNumber();

                if(collectInfoDetailDto.getIdx() != null){
                    // 수정
                    activityCode = ActivityCode.AC_30; // 개인정보 처리방침 수정
                    log.info("개인정보처리방침 수정 - 수정 대상 조회");
                    saveCollectInfo = collectInfoRepository.findById(collectInfoDetailDto.getIdx())
                            .orElseThrow(() -> new IllegalArgumentException("개인정보처리방침 수정 실패, 게시글을 발견할 수 없습니다. 요청 idx : " + collectInfoDetailDto.getIdx()));
                    log.info("개인정보처리방침 수정 - 수정자, 수정일시 세팅");
                    saveCollectInfo.setModifierIdx(adminIdx);
                    saveCollectInfo.setModifierName(adminName);
                    saveCollectInfo.setModifyDate(LocalDateTime.now());
                }else {
                    // 등록
                    activityCode = ActivityCode.AC_28; // 개인정보 처리방침 추가
                    log.info("개인정보처리방침 등록 - 등록자, 등록일시 세팅");
                    saveCollectInfo.setAdminIdx(adminIdx);
                    saveCollectInfo.setRegisterName(adminName);
                    saveCollectInfo.setRegdate(LocalDateTime.now());
                }

                log.info("개인정보처리방침 등록/수정 - 내용 세팅");
                if(collectInfoDetailDto.getTitle() != null){
                    saveCollectInfo.setTitle(collectInfoDetailDto.getTitle());
                }
                if(collectInfoDetailDto.getContent() != null){
                    saveCollectInfo.setContent(collectInfoDetailDto.getContent());
                }

                // 활동이력 -> 비정상 모드
                Integer activityHistoryIDX = activityHistoryService.insertActivityHistory(2, companyIdx, adminIdx, activityCode
                        , businessNumber+" - "+activityCode.getDesc()+" 시도 이력", "", ip, 0);
                try{
                    log.info("개인정보처리방침 등록/수정 시작.");
                    Integer savedIdx = collectInfoRepository.save(saveCollectInfo).getIdx();
                    log.info("개인정보처리방침 등록/수정 완료. idx : " + savedIdx);
                    // 활동이력 -> 정상 모드
                    activityHistoryService.updateActivityHistory(activityHistoryIDX,
                            businessNumber+" - "+activityCode.getDesc()+" 완료 이력", "", 1);
                    return ResponseEntity.ok(res.success(data));
                } catch(Exception e){
                    // 활동이력 -> 정상 모드
                    activityHistoryService.updateActivityHistory(activityHistoryIDX,
                            businessNumber+" - "+activityCode.getDesc()+" 실패 이력", "", 1);
                    e.getStackTrace();
                    log.error("개인정보처리방침 등록/수정 실패");
                    // TODO errorCode 추가 -  개인정보 처리방침 저장을 실패했습니다. 시스템 관리자에게 문의해주세요.
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO052.getCode(), ResponseErrorCode.KO052.getDesc()));
                }
            }
        }
    }

    /**
     * 개인정보처리방침 삭제
     * @param userRole  사용자 권한
     * @param email     사용자 이메일
     * @param idx       개인정보처리방침 인덱스
     */
    @Transactional
    public ResponseEntity<Map<String, Object>> collectInfoDelete(String userRole, String email, Integer idx) {
        log.info("collectInfoDelete 호출, userRole : " + userRole);
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        // 접속정보에서 필요한 정보 가져오기.
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : " + email));
        AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

        if (adminCompanyInfoDto == null) {
            log.error("회사 정보가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "회사 정보가 " + ResponseErrorCode.KO004.getDesc()));
        } else {
            // TODO 메뉴 권한에 대한 체크 부분은 프론트 단에서 진행하기로 함.
            if(!"[MASTER]".equals(userRole) && !"[ADMIN]".equals(userRole)){
                log.error("접근권한이 없습니다. userRole : " + userRole);
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
            }else{
                if(idx == null){
                    log.error("idx 값을 확인 할 수 없습니다.");
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO051.getCode(), ResponseErrorCode.KO051.getDesc()));
                }else{
                    log.info("개인정보 처리방침 삭제 시작.");

                    ActivityCode activityCode = ActivityCode.AC_29;
                    Integer adminIdx = admin.getIdx();
                    Integer companyIdx = adminCompanyInfoDto.getCompanyIdx();
                    String ip = CommonUtil.clientIp();
                    String businessNumber = adminCompanyInfoDto.getBusinessNumber();

                    // 활동이력 -> 비정상 모드
                    Integer activityHistoryIDX = activityHistoryService.insertActivityHistory(2, companyIdx, adminIdx, activityCode
                            , businessNumber+" - "+activityCode.getDesc()+" 시도 이력", "", ip, 0);

                    collectInfoRepository.deleteById(idx);
                    if(!collectInfoRepository.existsById(idx)){
                        // 활동이력 -> 정상 모드
                        activityHistoryService.updateActivityHistory(activityHistoryIDX,
                                businessNumber+" - "+activityCode.getDesc()+" 성공 이력", "", 1);
                        log.info("개인정보 처리방침 삭제 완료. idx : "+idx);
                        return ResponseEntity.ok(res.success(data));
                    }else{
                        // 활동이력 -> 정상 모드
                        activityHistoryService.updateActivityHistory(activityHistoryIDX,
                                businessNumber+" - "+activityCode.getDesc()+" 실패 이력", "", 1);
                        // TODO errorCode 추가 -  개인정보 처리방침 삭제 실패했습니다. 시스템 관리자에게 문의해주세요.
                        log.error("개인정보 처리방침 삭제에 실패했습니다. 관리자에게 문의해주세요. idx : " + idx);
                        return ResponseEntity.ok(res.fail(ResponseErrorCode.KO052.getCode(), ResponseErrorCode.KO052.getDesc()));
                    }
                }
            }
        }
    }

//    @Transactional
//    public ResponseEntity<Map<String, Object>> collectInfoDeleteAll(Integer companyIdx) {
//        log.info("collectInfoDeleteAll 호출, companyIdx : " + companyIdx);
//        if(companyIdx != null){
//            log.info("개인정보 수집 및 이용 안내 전체 삭제 시작.");
//            List<Integer> collectInfoIdxLists = collectInfoRepository.findCollectInfoIdxByCompayId(companyIdx);
//            if(!collectInfoIdxLists.isEmpty()){
//                for(Integer idx : collectInfoIdxLists){
//                    collectInfoRepository.deleteById(idx);
//                }
//                log.info("개인정보 수집 및 이용 안내 전체 삭제 완료. 삭제 건 수 : " +collectInfoIdxLists.size());
//            }else{
//                log.info("개인정보 수집 및 이용 안내 전체 삭제 완료. 삭제 건 수 : 0");
//            }
//            return ResponseEntity.ok(res.success(data));
//        }else{
//            log.error("idx 값을 확인 할 수 없습니다.");
//            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getDesc()));
//        }
//    }
}

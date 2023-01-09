package com.app.kokonut.collectInformation;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.collectInformation.dto.CollectInfoDetailDto;
import com.app.kokonut.collectInformation.dto.CollectInfoListDto;
import com.app.kokonut.collectInformation.dto.CollectInfoSearchDto;
import com.app.kokonut.collectInformation.entity.CollectInformation;
import com.app.kokonut.company.CompanyRepository;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
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
import java.util.Optional;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : CollectInfomationService 개인정보 처리방침 - 개인정보 수집 및 이용 안내 서비스
 */
@Slf4j
@Service
public class CollectInformationService {
    private final CompanyRepository companyRepository;
    private final AjaxResponse res = new AjaxResponse();
    private final HashMap<String, Object> data = new HashMap<>();

    private final CollectInformationRepository collectInfoRepository;
    private final AdminRepository adminRepository;
    public CollectInformationService(CollectInformationRepository collectInfoRepository, AdminRepository adminRepository,
                                     CompanyRepository companyRepository) {
        this.collectInfoRepository = collectInfoRepository;
        this.adminRepository = adminRepository;
        this.companyRepository = companyRepository;
    }

    public ResponseEntity<Map<String, Object>> collectInfoList(String userRole, String email, CollectInfoSearchDto collectInfoSearchDto, Pageable pageable) {
        log.info("collectInfoList 호출, userRole : " + userRole);
        // 접속정보에서 companyIdx 가져오기
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : " + email));
        // TODO 메뉴 권한에 대한 체크 부분
        if ("[MASTER]".equals(userRole) || "[ADMIN]".equals(userRole)) {
            Integer companyIdx = admin.getCompanyIdx();
            log.info("개인정보 수집 및 이용 안내 목록 조회");
            Page<CollectInfoListDto> collectInfoListDtos = collectInfoRepository.findCollectInfoPage(companyIdx, collectInfoSearchDto, pageable);
            return ResponseEntity.ok(res.ResponseEntityPage(collectInfoListDtos));
        } else {
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
        }
    }
    public ResponseEntity<Map<String,Object>> collectInfoDetail(String userRole, Integer idx) {
        log.info("collectInfoDetail 호출, userRole : " + userRole);
        // TODO 메뉴 권한에 대한 체크 부분
        if("[MASTER]".equals(userRole) || "[ADMIN]".equals(userRole)){
            if(idx != null){
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
            }else{
                log.error("idx 값을 확인 할 수 없습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO051.getCode(), ResponseErrorCode.KO051.getDesc()));
            }
        }else{
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
        }
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> collectInfoSave(String userRole, String email, CollectInfoDetailDto collectInfoDetailDto) {
        log.info("collectInfoSave 호출, userRole : " + userRole);
        // TODO 메뉴 권한에 대한 체크 부분
        if("[MASTER]".equals(userRole) || "[ADMIN]".equals(userRole)){
            // 접속 정보에서 관리자 정보 가져오기, idx, name
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+email));
            Integer adminIdx = admin.getIdx();
            String adminName = admin.getName();

            if(collectInfoDetailDto.getIdx() != null){
                log.info("개인정보 수집 및 이용 안내 등록");
                log.info("등록자, 등록일시 세팅");
                CollectInformation insertCollectInfo = new CollectInformation();
                insertCollectInfo.setAdminIdx(adminIdx);
                insertCollectInfo.setRegisterName(adminName);
                insertCollectInfo.setRegdate(LocalDateTime.now());
                log.info("내용 세팅");
                insertCollectInfo.setTitle(collectInfoDetailDto.getTitle());
                insertCollectInfo.setContent(collectInfoDetailDto.getContent());
                Integer savedIdx = collectInfoRepository.save(insertCollectInfo).getIdx();
                log.info("개인정보 수집 및 이용 안내 등록 완료. idx : " + savedIdx);
            }else{
                log.info("개인정보 수집 및 이용 안내 수정, idx : " + collectInfoDetailDto.getIdx());
                Optional<CollectInformation> updateCollectInfo = collectInfoRepository.findById(collectInfoDetailDto.getIdx());
                if(updateCollectInfo.isEmpty()){
                    log.error("개인정보 수집 및 이용 안내 수정 실패, 게시글을 발견할 수 없습니다. 요청 idx : " + collectInfoDetailDto.getIdx());
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO052.getCode(), ResponseErrorCode.KO052.getDesc()));
                }else{
                    log.info("수정자, 수정일시 세팅");
                    updateCollectInfo.get().setModifierIdx(adminIdx);
                    updateCollectInfo.get().setModifierName(adminName);
                    updateCollectInfo.get().setModifyDate(LocalDateTime.now());
                    log.info("내용 세팅");
                    if(collectInfoDetailDto.getTitle() != null){
                        updateCollectInfo.get().setTitle(collectInfoDetailDto.getTitle());
                    }
                    if(collectInfoDetailDto.getContent() != null){
                        updateCollectInfo.get().setContent(collectInfoDetailDto.getContent());
                    }
                    Integer updatedIdx = collectInfoRepository.save(updateCollectInfo.get()).getIdx();
                    log.info("개인정보 수집 및 이용 안내 수정 완료. idx : " + updatedIdx);
                }
            }
            return ResponseEntity.ok(res.success(data));
        }else{
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
        }
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> collectInfoDelete(String userRole, String email, Integer idx) {
        log.info("collectInfoDelete 호출, userRole : " + userRole);
        // TODO 메뉴 권한에 대한 체크 부분
        if("[MASTER]".equals(userRole) || "[ADMIN]".equals(userRole)){
            if(idx != null){
                log.info("개인정보 수집 및 이용 안내 삭제 시작.");
                collectInfoRepository.deleteById(idx);
                // TODO 삭제 여부 체크. 테스트 코드 확인 후 다른 삭제 로직에도 추가할 것.
                if(!collectInfoRepository.existsById(idx)){
                    log.info("개인정보 수집 및 이용 안내 삭제 완료. idx : "+idx);
                    data.put("idx",  idx);
                    return ResponseEntity.ok(res.success(data));
                }else{
                    log.error("개인정보 수집 및 이용 안내 삭제에 실패했습니다. 관리자에게 문의해주세요. idx : " + idx);
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO052.getCode(), ResponseErrorCode.KO052.getDesc()));
                }
            }else{
                log.error("idx 값을 확인 할 수 없습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO051.getCode(), ResponseErrorCode.KO051.getDesc()));
            }
        }else{
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
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

package com.app.kokonut.totalDBDownload;

import com.app.kokonut.activityHistory.ActivityHistoryService;
import com.app.kokonut.activityHistory.dto.ActivityCode;
import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.dtos.AdminCompanyInfoDto;
import com.app.kokonut.admin.dtos.AdminOtpKeyDto;
import com.app.kokonut.auth.jwt.config.GoogleOTP;
import com.app.kokonut.totalDBDownload.dtos.TotalDbDownloadListDto;
import com.app.kokonut.totalDBDownload.dtos.TotalDbDownloadSearchDto;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import com.app.kokonut.woody.common.component.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Woody
 * Date : 2023-01-13
 * Time :
 * Remark : TotalDbDownload Service
 */
@Slf4j
@Service
public class TotalDbDownloadService {

    private final GoogleOTP googleOTP;
    private final AdminRepository adminRepository;
    private final ActivityHistoryService activityHistoryService;
    private final TotalDbDownloadRepository totalDbDownloadRepository;

    @Autowired
    public TotalDbDownloadService(GoogleOTP googleOTP, AdminRepository adminRepository,
                                  ActivityHistoryService activityHistoryService, TotalDbDownloadRepository totalDbDownloadRepository){
        this.googleOTP = googleOTP;
        this.adminRepository = adminRepository;
        this.activityHistoryService = activityHistoryService;
        this.totalDbDownloadRepository = totalDbDownloadRepository;
    }

    // 개인정보 DB 데이터 전체 다운로드 요청
    @Transactional
    public ResponseEntity<Map<String, Object>> userDbDataDownloadApply(String otpValue, String reason, String email) {
        log.info("userDbDataDownloadApply 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        log.info("otpValue : "+ otpValue);
        log.info("reason : "+ reason);
        log.info("email : "+email);

        // 해당 이메일을 통해 회사 IDX 조회
        AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

        int adminIdx;
        int companyIdx;
        String businessNumber;

        if(adminCompanyInfoDto == null) {
            log.error("이메일 정보가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 "+ResponseErrorCode.KO004.getDesc()));
        }
        else {
            adminIdx = adminCompanyInfoDto.getAdminIdx(); // modifierIdx
            companyIdx = adminCompanyInfoDto.getCompanyIdx(); // companyIdx
            businessNumber = adminCompanyInfoDto.getBusinessNumber(); // tableName
        }

        AdminOtpKeyDto adminOtpKeyDto = adminRepository.findByOtpKey(email);
        if(adminOtpKeyDto != null) {
            // OTP 검증 절차
            boolean auth = googleOTP.checkCode(otpValue, adminOtpKeyDto.getOtpKey());
//			log.info("auth : " + auth);

            if (!auth) {
                log.error("입력된 구글 OTP 값이 일치하지 않습니다. 확인해주세요.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO012.getCode(), ResponseErrorCode.KO012.getDesc()));
            }
        } else {
            log.error("등록된 OTP가 존재하지 않습니다. 구글 OTP 2단계 인증을 등록해주세요.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO011.getCode(), ResponseErrorCode.KO011.getDesc()));
        }

        // 회원 DB데이터 다운로드 요청 코드
        ActivityCode activityCode = ActivityCode.AC_22;
        // 활동이력 저장 -> 비정상 모드
        String ip = CommonUtil.clientIp();
        Integer activityHistoryIDX = activityHistoryService.insertActivityHistory(2, companyIdx, adminIdx, activityCode, businessNumber+" - "+activityCode.getDesc()+" 시도 이력", "", ip, 0);

        // 회원 DB데이터 다운로드 요청건 insert
        TotalDbDownload totalDbDownload = new TotalDbDownload();
        totalDbDownload.setAdminIdx(adminIdx);
        totalDbDownload.setReason(reason);
        totalDbDownload.setState(1);
        totalDbDownload.setApplyDate(LocalDateTime.now());
        totalDbDownload.setRegdate(LocalDateTime.now());
        TotalDbDownload saveTotalDbDownload = totalDbDownloadRepository.save(totalDbDownload);

        if(saveTotalDbDownload.getIdx() != null){
            log.info("회원 DB 데이터 다운로드 요청 완료");
            activityHistoryService.updateActivityHistory(activityHistoryIDX,
                    businessNumber+" - "+activityCode.getDesc()+" 완료 이력", "", 1);
        } else {
            log.error("회원 DB 데이터 다운로드 요청 실패");
            activityHistoryService.updateActivityHistory(activityHistoryIDX,
                    businessNumber+" - "+activityCode.getDesc()+" 실패 이력", "필드 삭제 조건에 부합하지 않습니다.", 1);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO068.getCode(), ResponseErrorCode.KO068.getDesc()));
        }

        return ResponseEntity.ok(res.success(data));
    }

    // 개인정보 DB 데이터 다운로드 요청건 리스트
    public ResponseEntity<Map<String, Object>> userDbDataDownloadList(TotalDbDownloadSearchDto totalDbDownloadSearchDto, String email, Pageable pageable) {
        log.info("userDbDataDownloadList 호출");

        AjaxResponse res = new AjaxResponse();

        log.info("totalDbDownloadSearchDto : "+ totalDbDownloadSearchDto);
        log.info("email : "+email);

        // 해당 이메일을 통해 회사 IDX 조회
        AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

        String businessNumber;

        if(adminCompanyInfoDto == null) {
            log.error("이메일 정보가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 "+ResponseErrorCode.KO004.getDesc()));
        }
        else {
            businessNumber = adminCompanyInfoDto.getBusinessNumber(); // tableName
        }

        Page<TotalDbDownloadListDto> totalDbDownloadListDtoList = totalDbDownloadRepository.findByTotalDbDownloadList(totalDbDownloadSearchDto, businessNumber, pageable);

        return ResponseEntity.ok(res.ResponseEntityPage(totalDbDownloadListDtoList));
    }

    // 개인정보 DB 데이터 다운로드 시작
    public void userDbDataDownloadStart(Integer idx, String email) {
        log.info("userDbDataDownloadStart 호출");

        log.info("idx : "+ idx);
        log.info("email : "+email);
        

    }



}

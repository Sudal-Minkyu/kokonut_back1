package com.app.kokonut.activityHistory;

import com.app.kokonut.activityHistory.dto.*;
import com.app.kokonut.admin.Admin;
import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.common.AjaxResponse;
import com.app.kokonut.common.ResponseErrorCode;
import com.app.kokonut.common.component.Utils;
import com.app.kokonut.configs.ExcelService;
import com.app.kokonut.totalDBDownloadHistory.TotalDbDownloadHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Woody
 * Date : 2022-11-03
 * Remark :
 */
@Slf4j
@Service
public class ActivityHistoryService {

    private final AdminRepository adminRepository;

    private final ActivityHistoryRepository activityHistoryRepository;

    private final ExcelService excelService;
    private final TotalDbDownloadHistoryRepository downloadHistoryRepository;

    @Autowired
    public ActivityHistoryService(AdminRepository adminRepository, ActivityHistoryRepository activityHistoryRepository,
                                  ExcelService excelService, TotalDbDownloadHistoryRepository downloadHistoryRepository) {
        this.adminRepository = adminRepository;
        this.activityHistoryRepository = activityHistoryRepository;
        this.excelService = excelService;
        this.downloadHistoryRepository = downloadHistoryRepository;
    }

    // Column 리스트 조회
    public List<Column> findByActivityHistoryColumnList() {
        return activityHistoryRepository.findByActivityHistoryColumnList();
    }

    /**
     * 활동내역 리스트
     */
    public ResponseEntity<Map<String,Object>> findByActivityHistoryList(String email, String searchText, String stime, String actvityType, Pageable pageable) {
        log.info("findByActivityHistoryList 호출");

        log.info("email : "+email);
        log.info("searchText : "+searchText);
        log.info("stime : "+stime);
        log.info("actvityType : "+actvityType);

        AjaxResponse res = new AjaxResponse();

        // 접속한 사용자 인덱스
        Admin admin = adminRepository.findByKnEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+email));

        ActivityHistorySearchDto activityHistorySearchDto = new ActivityHistorySearchDto();
        activityHistorySearchDto.setCompanyId(admin.getCompanyId());
        activityHistorySearchDto.setSearchText(searchText);

        if(!actvityType.equals("")) {
            List<ActivityCode> activityCodeList = new ArrayList<>();
            String[] employeeNamesSplit = actvityType.split(",");
            ArrayList<String> actvityTypeList = new ArrayList<>(Arrays.asList(employeeNamesSplit));

            for(String actvityCode : actvityTypeList) {
                activityCodeList.add(ActivityCode.valueOf(actvityCode));
            }
            activityHistorySearchDto.setActivityCodeList(activityCodeList);
        }

        if(!stime.equals("")) {
            List<LocalDateTime> stimeList = Utils.getStimeList(stime);
            activityHistorySearchDto.setStimeStart(stimeList.get(0));
            activityHistorySearchDto.setStimeEnd(stimeList.get(1));
        }
        else {
            log.info("활동날짜는 필수값 입니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO077.getCode(), ResponseErrorCode.KO077.getDesc()));
        }

        Page<ActivityHistoryListDto> activityHistoryListDtos = activityHistoryRepository.findByActivityHistoryList(activityHistorySearchDto, pageable);
        if(activityHistoryListDtos.getTotalPages() == 0) {
            log.info("조회된 데이터가 없습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO003.getCode(), ResponseErrorCode.KO003.getDesc()));
        }

        return ResponseEntity.ok(res.ResponseEntityPage(activityHistoryListDtos));
    }

    // 활동내역 정보 리스트 조회
    public List<ActivityHistoryInfoListDto> findByActivityHistoryBycompanyIdAndTypeList(Long companyId, Integer ahType) {
        return activityHistoryRepository.findByActivityHistoryBycompanyIdAndTypeList(companyId, ahType);
    }

//    public List<Map<String, Object>> SelectActivityHistoryList(Integer type, Long companyId){
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("TYPE", type);
//        map.put("COMPANY_IDX", companyId);
//
//        return activityHistoryRepository.SelectByTypeAndcompanyId(map);
//    }

//    /**
//     * 활동내역 리스트 Count
//     */
//    public int SelectActivityHistoryListCount(HashMap<String, Object> paramMap) {
//        return activityHistoryRepository.SelectActivityHistoryListCount(paramMap);
//    }

    /**
     * 활동내역 상세보기
     * @param idx
     */
    public ActivityHistoryDto findByActivityHistoryByIdx(Long idx) {
        return activityHistoryRepository.findByActivityHistoryByIdx(idx);
    }

    /**
     * 활동내역 insert
     * @param ahType - 1:고객정보처리, 2:관리자활동, 3:회원DB관리이력, 4:정보제공이력
     * @param companyId
     * @param activityCode
     * @param ahActivityDetail - 활동상세내역
     * @param ahReason - 사유
     * @param ahIpAddr - 접속IP주소
     * @param ahState - 0:비정상, 1:정상
     * @return save IDX
     * 기존 코코넛 : InsertActivityHistory
     */
    public Long insertActivityHistory(int ahType, Long companyId, Long adminId, ActivityCode activityCode,
                                      String ahActivityDetail, String ahReason, String ahIpAddr, int ahState) {

        ActivityHistory activityHistory = new ActivityHistory();
        activityHistory.setAhType(ahType);
        activityHistory.setAdminId(adminId);
        activityHistory.setActivityCode(activityCode);
        activityHistory.setAhActivityDetail(ahActivityDetail);
        activityHistory.setAhReason(ahReason);
        activityHistory.setAhIpAddr(ahIpAddr);
        activityHistory.setAhState(ahState);
        activityHistory.setInsert_date(LocalDateTime.now());

        activityHistory = activityHistoryRepository.save(activityHistory);

        return activityHistory.getAhId();
    }

    /**
     * 활동내역 Update
     * @param ahId - 키값
     * @param activityDetail - 활동상세내역
     * @param ahReason - 사유
     * @param ahState - 0:비정상, 1:정상
     * 기존 코코넛 : UpdateActivityHistory
     */
    public void updateActivityHistory(Long ahId, String activityDetail, String ahReason, int ahState) {
        ActivityHistory activityHistory = activityHistoryRepository.findById(ahId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ActivityHistory' 입니다."));

        activityHistory.setAhActivityDetail(activityDetail);
        activityHistory.setAhReason(ahReason);
        activityHistory.setAhState(ahState);

        activityHistoryRepository.save(activityHistory);
    }

    /**
     * 활동내역 삭제
     * @param idx
     */
//    public void DeleteActivityHistoryByIdx(int idx) {
//        activityHistoryRepository.DeleteActivityHistoryByIdx(idx);
//    }
    public void deleteActivityHistoryByIdx(Long idx) {
        ActivityHistory activityHistory = activityHistoryRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ActivityHistory' 입니다."));

        activityHistoryRepository.delete(activityHistory);
    }

    /**
     * 활동내역 사유 변경
     * @param ahId
     * @param ahReason
     */
//    public void UpdateActivityHistoryReasonByIdx(HashMap<String, Object> paramMap) {
//        activityHistoryRepository.UpdateActivityHistoryReasonByIdx(paramMap);
//    }
    public void updateActivityHistoryReasonByIdx(Long ahId, String ahReason) {
        ActivityHistory activityHistory = activityHistoryRepository.findById(ahId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ActivityHistory' 입니다."));
        activityHistory.setAhReason(ahReason);
        activityHistoryRepository.save(activityHistory);
    }

    /**
     * 활동내역 상세보기 (companyId, reason, activityIdx)
     * @param companyId
     * @param ahReason
     */
    public ActivityHistoryDto findByActivityHistoryBycompanyIdAndReasonaAndAtivityIdx(Long companyId, String ahReason) {
        return activityHistoryRepository.findByActivityHistoryBycompanyIdAndReasonaAndAtivityIdx(companyId, ahReason);
    }

    /**
     * 활동내역 통계
     * @param companyId
     * @param day - 날짜
     */
    public ActivityHistoryStatisticsDto findByActivityHistoryStatistics(Long companyId, int day) {
        return activityHistoryRepository.findByActivityHistoryStatistics(companyId, day);
    }

    /**
     * 유효기간 지난 활동내역 삭제
     * @param activityIdx 활동IDX
     * @param month 달
     */
//    public void DeleteExpiredActivityHistory(int activityIdx, int month) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("activityIdx", activityIdx);
//        map.put("month", month);
//
//        activityHistoryRepository.DeleteExpiredActivityHistory(map);
//    }
    public void deleteExpiredActivityHistory(int activityIdx, int month) {
        activityHistoryRepository.deleteExpiredActivityHistory(activityIdx, month);
    }




}

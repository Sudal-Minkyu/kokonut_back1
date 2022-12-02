package com.app.kokonut.activityHistory;

import com.app.kokonut.activityHistory.dto.*;
import com.app.kokonut.woody.been.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Woody
 * Date : 2022-11-03
 * Remark :
 */
@Service
public class ActivityHistoryService {

    private final ActivityHistoryRepository activityHistoryRepository;

    @Autowired
    public ActivityHistoryService(ActivityHistoryRepository activityHistoryRepository) {
        this.activityHistoryRepository = activityHistoryRepository;
    }

//    public String[] TYPE_LIST = {
//            "",
//            "고객정보처리",
//            "관리자활동",
//            "회원DB관리이력"
//    };

//    public String[] ACTIVITY_LIST = {
//            "",
//            "로그인",
//            "회원정보변경",
//            "회원정보삭제",
//            "관리자추가",
//            "관리자권한변경",
//            "처리이력다운로드",
//            "활동이력다운로드",
//            "고객정보 열람",
//            "고객정보 다운로드",
//            "고객정보 처리",
//            "회원정보 DB관리 변경",
//            "회원DB 항목 관리 변경",
//            "회원 관리 등록",
//            "정보제공 목록",
//            "정보 파기 관리",
//            "테이블 생성",
//            "전체DB다운로드",
//            "회원 관리 변경"
//    };

//    public String[] STATE_LIST = {
//            "정상",
//            "비정상"
//    };

    // Column 리스트 조회
    public List<Column> findByActivityHistoryColumnList() {
        return activityHistoryRepository.findByActivityHistoryColumnList();
    }

    /**
     * 활동내역 리스트
     */
    public List<ActivityHistoryListDto> findByActivityHistoryList(ActivityHistorySearchDto activityHistorySearchDto) {
        return activityHistoryRepository.findByActivityHistoryList(activityHistorySearchDto);
    }

    // 활동내역 정보 리스트 조회
    public List<ActivityHistoryInfoListDto> findByActivityHistoryByCompanyIdxAndTypeList(Integer companyIdx, Integer type) {
        return activityHistoryRepository.findByActivityHistoryByCompanyIdxAndTypeList(companyIdx, type);
    }

//    public List<Map<String, Object>> SelectActivityHistoryList(Integer type, Integer companyIdx){
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("TYPE", type);
//        map.put("COMPANY_IDX", companyIdx);
//
//        return activityHistoryRepository.SelectByTypeAndCompanyIdx(map);
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
    public ActivityHistoryDto findByActivityHistoryByIdx(Integer idx) {
        return activityHistoryRepository.findByActivityHistoryByIdx(idx);
    }

    /**
     * 활동내역 insert
     * @param type - 1:고객정보처리, 2:관리자활동, 3:회원DB관리이력
     * @param companyIdx
     * @param activityIdx - 활동내역(1:로그인,2:회원정보변경,3:회원정보삭제,4:관리자추가,5:관리자권한변경,6:처리이력다운로드,7:활동이력다운로드,8:고객정보 열람,9:고객정보 다운로드,10:고객정보 처리,11:회원정보DB관리 변경,12:회원DB 항목 관리 변경,13:회원 관리 변경)
     * @param activityDetail - 활동상세내역
     * @param reason - 사유
     * @param ipAddr - 접속IP주소
     * @param state - 0:비정상, 1:정상
     * @return
     */
//    public HashMap<String, Object> InsertActivityHistory(int type, int companyIdx, int adminIdx, int activityIdx, String activityDetail, String reason, String ipAddr, int state) {
//        HashMap<String, Object> returnMap = new HashMap<String, Object>();
//
//        try {
//            HashMap<String, Object> paramMap = new HashMap<String, Object>();
//            paramMap.put("type", type);
//            paramMap.put("companyIdx", companyIdx);
//            paramMap.put("adminIdx", adminIdx);
//            paramMap.put("activityIdx", activityIdx);
//            paramMap.put("activityDetail", activityDetail);
//            paramMap.put("reason", reason);
//            paramMap.put("ipAddr", ipAddr);
//            paramMap.put("state", state);
//
//            activityHistoryRepository.InsertActivityHistory(paramMap);
//
//            returnMap.put("idx", paramMap.get("idx").toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return returnMap;
//    }
    public Integer insertActivityHistory(int type, int companyIdx, int adminIdx, int activityIdx, String activityDetail, String reason, String ipAddr, int state) {

        ActivityHistory activityHistory = new ActivityHistory();
        activityHistory.setType(type);
        activityHistory.setCompanyIdx(companyIdx);
        activityHistory.setAdminIdx(adminIdx);
        activityHistory.setActivityIdx(activityIdx);
        activityHistory.setActivityDetail(activityDetail);
        activityHistory.setReason(reason);
        activityHistory.setIpAddr(ipAddr);
        activityHistory.setState(state);
        activityHistory.setRegdate(new Date());

        activityHistory = activityHistoryRepository.save(activityHistory);

        return activityHistory.getIdx();
    }

    /**
     * 활동내역 Update
     * @param idx - 키값
     * @param activityDetail - 활동상세내역
     * @param reason - 사유
     * @param state - 0:비정상, 1:정상
     */
//    public void UpdateActivityHistory(int idx, String activityDetail, String reason, int state) {
//        HashMap<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("idx", idx);
//        paramMap.put("activityDetail", activityDetail);
//        paramMap.put("reason", reason);
//        paramMap.put("state", state);
//
//        activityHistoryRepository.UpdateActivityHistory(paramMap);
//    }
    public void updateActivityHistory(int idx, String activityDetail, String reason, int state) {

        ActivityHistory activityHistory = activityHistoryRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ActivityHistory' 입니다."));

        activityHistory.setActivityDetail(activityDetail);
        activityHistory.setReason(reason);
        activityHistory.setState(state);

        activityHistoryRepository.save(activityHistory);
    }

    /**
     * 활동내역 삭제
     * @param idx
     */
//    public void DeleteActivityHistoryByIdx(int idx) {
//        activityHistoryRepository.DeleteActivityHistoryByIdx(idx);
//    }
    public void deleteActivityHistoryByIdx(int idx) {
        ActivityHistory activityHistory = activityHistoryRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ActivityHistory' 입니다."));

        activityHistoryRepository.delete(activityHistory);
    }

    /**
     * 활동내역 사유 변경
     * @param reason
     * @param idx
     */
//    public void UpdateActivityHistoryReasonByIdx(HashMap<String, Object> paramMap) {
//        activityHistoryRepository.UpdateActivityHistoryReasonByIdx(paramMap);
//    }
    public void updateActivityHistoryReasonByIdx(int idx, String reason) {
        ActivityHistory activityHistory = activityHistoryRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ActivityHistory' 입니다."));
        activityHistory.setReason(reason);
        activityHistoryRepository.save(activityHistory);
    }

    /**
     * 활동내역 상세보기 (companyIdx, reason, activityIdx)
     * @param companyIdx
     * @param reason
     * @param activityIdx
     */
    public ActivityHistoryDto findByActivityHistoryByCompanyIdxAndReasonaAndAtivityIdx(Integer companyIdx, String reason, Integer activityIdx) {
        return activityHistoryRepository.findByActivityHistoryByCompanyIdxAndReasonaAndAtivityIdx(companyIdx, reason, activityIdx);
    }

    /**
     * 활동내역 통계
     * @param companyIdx
     * @param day - 날짜
     */
    public ActivityHistoryStatisticsDto findByActivityHistoryStatistics(Integer companyIdx, int day) {
        return activityHistoryRepository.findByActivityHistoryStatistics(companyIdx, day);
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

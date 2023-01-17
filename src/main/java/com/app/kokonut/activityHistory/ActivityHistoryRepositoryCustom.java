package com.app.kokonut.activityHistory;

import com.app.kokonut.activityHistory.dto.*;

import java.util.List;

/**
 * @author Woody
 * Date : 2022-11-03
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 ActivityHistory Sql 쿼리호출
 */
public interface ActivityHistoryRepositoryCustom {

    List<ActivityHistoryListDto> findByActivityHistoryList(ActivityHistorySearchDto activityHistorySearchDto); // SelectActivityHistoryList -> 변경후

    ActivityHistoryDto findByActivityHistoryByIdx(Integer idx); // SelectActivityHistoryByIdx -> 변경후

    ActivityHistoryDto findByActivityHistoryByCompanyIdxAndReasonaAndAtivityIdx(Integer companyIdx, String reason, Integer activityIdx); // SelectActivityHistoryByCompanyIdxAndReason -> 변경후

    List<ActivityHistoryInfoListDto> findByActivityHistoryByCompanyIdxAndTypeList(Integer companyIdx, Integer type); // SelectByTypeAndCompanyIdx -> 변경후

    List<Column> findByActivityHistoryColumnList(); // selectColumns -> 변경후

    ActivityHistoryStatisticsDto findByActivityHistoryStatistics(Integer companyIdx, int day); // SelectStatisticsActivityHistory -> 변경후

    void deleteExpiredActivityHistory(int activityIdx, int month);
}
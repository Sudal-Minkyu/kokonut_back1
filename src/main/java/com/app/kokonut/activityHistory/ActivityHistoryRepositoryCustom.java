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

    ActivityHistoryDto findByActivityHistoryByIdx(Long idx); // SelectActivityHistoryByIdx -> 변경후

    ActivityHistoryDto findByActivityHistoryBycompanyIdAndReasonaAndAtivityIdx(Long companyId, String ahReason); // SelectActivityHistoryBycompanyIdAndReason -> 변경후

    List<ActivityHistoryInfoListDto> findByActivityHistoryBycompanyIdAndTypeList(Long companyId, Integer ahType); // SelectByTypeAndcompanyId -> 변경후

    List<Column> findByActivityHistoryColumnList(); // selectColumns -> 변경후

    ActivityHistoryStatisticsDto findByActivityHistoryStatistics(Long companyId, int day); // SelectStatisticsActivityHistory -> 변경후

    void deleteExpiredActivityHistory(int activityIdx, int month);
}
package com.app.kokonut.activityHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityHistoryRepository extends JpaRepository<ActivityHistory, Long>, JpaSpecificationExecutor<ActivityHistory>, ActivityHistoryRepositoryCustom {

//    public void InsertActivityHistory(HashMap<String, Object> paramMap);
//
//    public void UpdateActivityHistory(HashMap<String, Object> paramMap);
//
//    public void DeleteActivityHistoryByIdx(int idx);
//
//    public void UpdateActivityHistoryReasonByIdx(HashMap<String, Object> paramMap);


//    public void DeleteActivityHistoryBycompanyId(Long companyId);
//
//    public void DeleteExpiredActivityHistory(Map<String, Object> map);

}
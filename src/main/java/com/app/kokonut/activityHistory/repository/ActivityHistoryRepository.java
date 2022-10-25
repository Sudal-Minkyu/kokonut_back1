package com.app.kokonut.activityHistory.repository;

import com.app.kokonut.activityHistory.entity.ActivityHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityHistoryRepository extends JpaRepository<ActivityHistory, Integer>, JpaSpecificationExecutor<ActivityHistory> {

}
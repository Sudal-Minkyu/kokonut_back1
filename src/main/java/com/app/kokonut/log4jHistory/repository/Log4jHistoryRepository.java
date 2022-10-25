package com.app.kokonut.log4jHistory.repository;

import com.app.kokonut.log4jHistory.entity.Log4jHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface Log4jHistoryRepository extends JpaRepository<Log4jHistory, Integer>, JpaSpecificationExecutor<Log4jHistory> {

}
package com.app.kokonut.emailHistory.repository;

import com.app.kokonut.emailHistory.entity.EmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmailHistoryRepository extends JpaRepository<EmailHistory, Integer>, JpaSpecificationExecutor<EmailHistory> {

}
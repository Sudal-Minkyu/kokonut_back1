package com.app.kokonut.refactor.emailHistory.repository;

import com.app.kokonut.refactor.emailHistory.entity.EmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmailHistoryRepository extends JpaRepository<EmailHistory, Integer>, JpaSpecificationExecutor<EmailHistory> {

}
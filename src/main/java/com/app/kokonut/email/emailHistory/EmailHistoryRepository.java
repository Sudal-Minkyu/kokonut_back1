package com.app.kokonut.email.emailHistory;

import com.app.kokonut.email.emailHistory.entity.EmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmailHistoryRepository extends JpaRepository<EmailHistory, Integer>, JpaSpecificationExecutor<EmailHistory> {

}
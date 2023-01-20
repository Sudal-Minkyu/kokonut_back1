package com.app.kokonut.refactor.privacyEmailHistory.repository;

import com.app.kokonut.refactor.privacyEmailHistory.entity.PrivacyEmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrivacyEmailHistoryRepository extends JpaRepository<PrivacyEmailHistory, Long>, JpaSpecificationExecutor<PrivacyEmailHistory> {

}
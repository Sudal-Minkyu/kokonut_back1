package com.app.kokonut.privacyEmailHistory.repository;

import com.app.kokonut.privacyEmailHistory.entity.PrivacyEmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrivacyEmailHistoryRepository extends JpaRepository<PrivacyEmailHistory, Integer>, JpaSpecificationExecutor<PrivacyEmailHistory> {

}
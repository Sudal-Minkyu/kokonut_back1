package com.app.kokonut.personalInfoDownloadHistory.repository;

import com.app.kokonut.personalInfoDownloadHistory.entity.PersonalInfoDownloadHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonalInfoDownloadHistoryRepository extends JpaRepository<PersonalInfoDownloadHistory, Integer>, JpaSpecificationExecutor<PersonalInfoDownloadHistory> {

}
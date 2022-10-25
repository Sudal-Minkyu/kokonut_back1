package com.app.kokonut.personalInfoProvisionHistory.repository;

import com.app.kokonut.personalInfoProvisionHistory.entity.PersonalInfoProvisionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonalInfoProvisionHistoryRepository extends JpaRepository<PersonalInfoProvisionHistory, Integer>, JpaSpecificationExecutor<PersonalInfoProvisionHistory> {

}
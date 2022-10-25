package com.app.kokonut.personalInfoProvision.repository;

import com.app.kokonut.personalInfoProvision.entity.PersonalInfoProvision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonalInfoProvisionRepository extends JpaRepository<PersonalInfoProvision, Integer>, JpaSpecificationExecutor<PersonalInfoProvision> {

}
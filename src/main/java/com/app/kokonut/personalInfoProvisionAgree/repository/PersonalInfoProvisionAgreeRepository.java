package com.app.kokonut.personalInfoProvisionAgree.repository;

import com.app.kokonut.personalInfoProvisionAgree.entity.PersonalInfoProvisionAgree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonalInfoProvisionAgreeRepository extends JpaRepository<PersonalInfoProvisionAgree, Integer>, JpaSpecificationExecutor<PersonalInfoProvisionAgree> {

}
package com.app.kokonut.emailGroup.repository;

import com.app.kokonut.emailGroup.entity.EmailGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmailGroupRepository extends JpaRepository<EmailGroup, Integer>, JpaSpecificationExecutor<EmailGroup> {

}
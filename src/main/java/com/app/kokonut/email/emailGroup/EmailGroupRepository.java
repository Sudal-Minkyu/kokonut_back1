package com.app.kokonut.email.emailGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface EmailGroupRepository extends JpaRepository<EmailGroup, Long>, JpaSpecificationExecutor<EmailGroup>, EmailGroupRepositoryCustom {


}
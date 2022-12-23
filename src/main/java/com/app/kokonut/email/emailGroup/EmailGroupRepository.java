package com.app.kokonut.email.emailGroup;

import com.app.kokonut.email.emailGroup.entity.EmailGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmailGroupRepository extends JpaRepository<EmailGroup, Integer>, JpaSpecificationExecutor<EmailGroup>, EmailGroupRepositoryCustom {

}
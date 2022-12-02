package com.app.kokonut.refactor.email.repository;

import com.app.kokonut.refactor.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmailRepository extends JpaRepository<Email, Integer>, JpaSpecificationExecutor<Email> {

}
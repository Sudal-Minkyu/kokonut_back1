package com.app.kokonut.refactor.privacyEmail.repository;

import com.app.kokonut.refactor.privacyEmail.entity.PrivacyEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrivacyEmailRepository extends JpaRepository<PrivacyEmail, Integer>, JpaSpecificationExecutor<PrivacyEmail> {

}
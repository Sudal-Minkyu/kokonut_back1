package com.app.kokonut.privacyEmail.repository;

import com.app.kokonut.privacyEmail.entity.PrivacyEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrivacyEmailRepository extends JpaRepository<PrivacyEmail, Integer>, JpaSpecificationExecutor<PrivacyEmail> {

}
package com.app.kokonut.alimtalkMessageRecipient.repository;

import com.app.kokonut.alimtalkMessageRecipient.entity.AlimtalkMessageRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlimtalkMessageRecipientRepository extends JpaRepository<AlimtalkMessageRecipient, Integer>, JpaSpecificationExecutor<AlimtalkMessageRecipient> {

}
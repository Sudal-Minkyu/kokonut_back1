package com.app.kokonut.alimtalkMessage.repository;

import com.app.kokonut.alimtalkMessage.entity.AlimtalkMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlimtalkMessageRepository extends JpaRepository<AlimtalkMessage, Integer>, JpaSpecificationExecutor<AlimtalkMessage> {

}
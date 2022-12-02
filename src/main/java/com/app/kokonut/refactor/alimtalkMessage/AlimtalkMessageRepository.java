package com.app.kokonut.refactor.alimtalkMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimtalkMessageRepository extends JpaRepository<AlimtalkMessage, Integer>, JpaSpecificationExecutor<AlimtalkMessage>, AlimtalkMessageRepositoryCustom {

}
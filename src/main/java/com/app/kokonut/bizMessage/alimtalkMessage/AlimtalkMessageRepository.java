package com.app.kokonut.bizMessage.alimtalkMessage;

import com.app.kokonut.bizMessage.alimtalkMessage.entity.AlimtalkMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimtalkMessageRepository extends JpaRepository<AlimtalkMessage, Integer>, JpaSpecificationExecutor<AlimtalkMessage>, AlimtalkMessageRepositoryCustom {

}
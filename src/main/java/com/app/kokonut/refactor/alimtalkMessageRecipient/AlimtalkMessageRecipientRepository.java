package com.app.kokonut.refactor.alimtalkMessageRecipient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimtalkMessageRecipientRepository extends JpaRepository<AlimtalkMessageRecipient, Integer>, JpaSpecificationExecutor<AlimtalkMessageRecipient>, AlimtalkMessageRecipientRepositoryCustom {

}
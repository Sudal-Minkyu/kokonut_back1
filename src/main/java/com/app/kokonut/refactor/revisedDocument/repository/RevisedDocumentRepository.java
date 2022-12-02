package com.app.kokonut.refactor.revisedDocument.repository;

import com.app.kokonut.refactor.revisedDocument.entity.RevisedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RevisedDocumentRepository extends JpaRepository<RevisedDocument, Integer>, JpaSpecificationExecutor<RevisedDocument> {

}
package com.app.kokonut.refactor.alimtalkTemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimtalkTemplateRepository extends JpaRepository<AlimtalkTemplate, Integer>, JpaSpecificationExecutor<AlimtalkTemplate>, AlimtalkTemplateRepositoryCustom {

}
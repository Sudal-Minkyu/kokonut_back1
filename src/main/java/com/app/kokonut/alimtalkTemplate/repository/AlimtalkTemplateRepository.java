package com.app.kokonut.alimtalkTemplate.repository;

import com.app.kokonut.alimtalkTemplate.entity.AlimtalkTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlimtalkTemplateRepository extends JpaRepository<AlimtalkTemplate, Integer>, JpaSpecificationExecutor<AlimtalkTemplate> {

}
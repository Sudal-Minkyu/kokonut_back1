package com.app.kokonut.refactor.collectInformation.repository;

import com.app.kokonut.refactor.collectInformation.entity.CollectInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CollectInformationRepository extends JpaRepository<CollectInformation, Integer>, JpaSpecificationExecutor<CollectInformation> {

}
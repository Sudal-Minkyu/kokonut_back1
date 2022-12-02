package com.app.kokonut.refactor.adminLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminLevelRepository extends JpaRepository<AdminLevel, Integer>, JpaSpecificationExecutor<AdminLevel>, AdminLevelRepositoryCustom {

}
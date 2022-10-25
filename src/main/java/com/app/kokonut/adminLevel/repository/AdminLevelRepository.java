package com.app.kokonut.adminLevel.repository;

import com.app.kokonut.adminLevel.entity.AdminLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminLevelRepository extends JpaRepository<AdminLevel, Integer>, JpaSpecificationExecutor<AdminLevel> {

}
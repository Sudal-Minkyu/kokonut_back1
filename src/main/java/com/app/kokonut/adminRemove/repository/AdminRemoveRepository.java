package com.app.kokonut.adminRemove.repository;

import com.app.kokonut.adminRemove.entity.AdminRemove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminRemoveRepository extends JpaRepository<AdminRemove, Integer>, JpaSpecificationExecutor<AdminRemove> {

}
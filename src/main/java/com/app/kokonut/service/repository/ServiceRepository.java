package com.app.kokonut.service.repository;

import com.app.kokonut.service.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceRepository extends JpaRepository<Service, Integer>, JpaSpecificationExecutor<Service> {

}
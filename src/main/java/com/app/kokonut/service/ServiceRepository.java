package com.app.kokonut.service;
import com.app.kokonut.service.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Joy
 * Date : 2023-01-09
 * Time :
 * Remark : ServiceRepository
 */
public interface ServiceRepository extends JpaRepository<Service, Integer>, JpaSpecificationExecutor<Service>, ServiceRepositoryCustom {

}
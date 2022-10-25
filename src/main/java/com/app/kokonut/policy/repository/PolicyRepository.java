package com.app.kokonut.policy.repository;

import com.app.kokonut.policy.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PolicyRepository extends JpaRepository<Policy, Integer>, JpaSpecificationExecutor<Policy> {

}
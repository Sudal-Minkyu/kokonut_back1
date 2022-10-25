package com.app.kokonut.apiKey.repository;

import com.app.kokonut.apiKey.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer>, JpaSpecificationExecutor<ApiKey> {

}
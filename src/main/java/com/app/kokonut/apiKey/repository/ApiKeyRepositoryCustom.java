package com.app.kokonut.apiKey.repository;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepositoryCustom {

    // ApiKey 조회
    ApiKeyKeyDto findByKey(String key); // SelectByKey -> 변경후

}
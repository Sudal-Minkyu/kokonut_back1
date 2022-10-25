package com.app.kokonut.apiKey.repository;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.dto.ApiKeyListDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ApiKeyRepositoryCustom {

    // ApiKey 조회
    ApiKeyKeyDto findByKey(String key); // SelectByKey -> 변경후

//    // ApiKey 리스트
//    List<ApiKeyListDto> findByKeyList(HashMap<String, Object> paramMap);
}
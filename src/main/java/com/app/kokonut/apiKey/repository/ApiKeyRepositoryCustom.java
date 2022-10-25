package com.app.kokonut.apiKey.repository;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.dto.ApiKeyListCountDto;
import com.app.kokonut.apiKey.dto.ApiKeyListAndDetailDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ApiKeyRepositoryCustom {

    // ApiKey 리스트 조회
    List<ApiKeyListAndDetailDto> findByApiKeyList(HashMap<String, Object> paramMap); // SelectApiKeyList -> 변경후

    // ApiKey 리스트의 Count 조회
    ApiKeyListCountDto findByApiKeyListCount(HashMap<String, Object> paramMap); // SelectApiKeyListCount -> 변경후

    // ApiKey 단일 조회(상세보기) : param -> idx
    ApiKeyListAndDetailDto findByApiKeyDetail(Integer idx); // SelectApiKeyByIdx -> 변경후

    // ApiKey 단일 조회 : param -> key
    ApiKeyKeyDto findByKey(String key); // SelectByKey -> 변경후




}
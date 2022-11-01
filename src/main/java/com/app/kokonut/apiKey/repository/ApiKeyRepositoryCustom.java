package com.app.kokonut.apiKey.repository;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.dto.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.dto.TestApiKeyExpiredListDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 ApiKey Sql 쿼리호출
 */
@Repository
public interface ApiKeyRepositoryCustom {

    // ApiKey 리스트 조회
    List<ApiKeyListAndDetailDto> findByApiKeyList(HashMap<String, Object> paramMap); // SelectApiKeyList -> 변경후

    // ApiKey 리스트의 Count 조회
    Long findByApiKeyListCount(HashMap<String, Object> paramMap); // SelectApiKeyListCount -> 변경후

    // ApiKey 단일 조회(상세보기) : param -> idx
    ApiKeyListAndDetailDto findByApiKeyDetail(Integer idx); // SelectApiKeyByIdx -> 변경후

    // ApiKey 단일 조회 : param -> key
    ApiKeyKeyDto findByKey(String key); // SelectByKey -> 변경후

    // TestApiKey 단일 조회 : param -> companyIdx, type = 2
    ApiKeyListAndDetailDto findByTestApiKeyByCompanyIdx(Integer companyIdx, Integer type); // SelectTestApiKeyByCompanyIdx -> 변경후

    // TestApiKey 중복 조회 : param -> key, type = 2
    Long findByTestApiKeyDuplicateCount(String key, Integer type); // SelectTestApiKeyDuplicateCount -> 변경후

    // ApiKey 단일 조회 : param -> companyIdx, type = 1, useYn = "Y"
    ApiKeyListAndDetailDto findByApiKeyByCompanyIdx(Integer companyIdx, Integer type, String useYn); // SelectApiKeyByCompanyIdx -> 변경후

    // ApiKey 중복 조회 : param -> key, type = 1
    Long findByApiKeyDuplicateCount(String key, Integer type); // SelectApiKeyDuplicateCount -> 변경후

    // TestApiKey 만료예정 리스트 조회
    List<TestApiKeyExpiredListDto> findByTestApiKeyExpiredList(HashMap<String, Object> paramMap, Integer type); // SelectTestApiKeyExpiredList -> 변경후



}
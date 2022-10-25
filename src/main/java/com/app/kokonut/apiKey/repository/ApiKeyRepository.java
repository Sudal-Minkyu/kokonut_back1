package com.app.kokonut.apiKey.repository;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark :
 */
@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer>, JpaSpecificationExecutor<ApiKey>, ApiKeyRepositoryCustom {

//     List<HashMap<String, Object>> SelectApiKeyList(HashMap<String, Object> paramMap); // 변경전 - Custom 완료
//
//     int SelectApiKeyListCount(HashMap<String, Object> paramMap); // 변경전 - Custom 완료
//
//     HashMap<String, Object> SelectByKey(String key); // 변경전 - Custom 완료
//
//     HashMap<String, Object> SelectApiKeyByIdx(int idx); // 변경전 - Custom 완료
//
//     void InsertApiKey(HashMap<String, Object> paramMap);
//
//     void UpdateApiKey(HashMap<String, Object> paramMap);
//
//     void DeleteApiKeyByIdx(int idx);
//
//     HashMap<String, Object> SelectTestApiKeyByCompanyIdx(HashMap<String, Object> paramMap);
//
//     int SelectApiKeyDuplicateCount(String key);
//
//     int SelectTestApiKeyDuplicateCount(String key);
//
//     HashMap<String, Object> SelectApiKeyByCompanyIdx(HashMap<String, Object> paramMap);
//
//     List<HashMap<String, Object>> SelectTestApiKeyExpiredList(HashMap<String, Object> paramMap);
//
//     void UpdateBlockKey(int companyIdx);
//
//     void UpdateTestKeyExpire(int companyIdx);
//
//     void DeleteApiKeyByCompanyIdx(int companyIdx);

}
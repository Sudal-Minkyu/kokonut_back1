package com.app.kokonut.apiKey.repository;

import com.app.kokonut.apiKey.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 ApiKeyDao라고 보면 됨
 */
@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer>, JpaSpecificationExecutor<ApiKey>, ApiKeyRepositoryCustom {

//    @Query("select a from ApiKey a where a.companyIdx = :companyIdx and a.type = :type")
    Optional<ApiKey> findApiKeyByCompanyIdxAndType(Integer companyIdx, Integer type);

//     List<HashMap<String, Object>> SelectApiKeyList(HashMap<String, Object> paramMap); // 변경전 - RepositoryCustom 완료 @@@@
//
//     int SelectApiKeyListCount(HashMap<String, Object> paramMap); // 변경전 - RepositoryCustom 완료 @@@@
//
//     HashMap<String, Object> SelectByKey(String key); // 변경전 - RepositoryCustom 완료 @@@@
//
//     HashMap<String, Object> SelectApiKeyByIdx(int idx); // 변경전 - RepositoryCustom 완료 @@@@
//
//     void InsertApiKey(HashMap<String, Object> paramMap); // 변경전 - Service 완료 @@@@
//
//     void UpdateApiKey(HashMap<String, Object> paramMap); // 변경전 - Service 완료 @@@@
//
//     void DeleteApiKeyByIdx(int idx); // 변경전 - Service 완료 @@@@
//
//     HashMap<String, Object> SelectTestApiKeyByCompanyIdx(HashMap<String, Object> paramMap); // 변경전 - RepositoryCustom 완료 @@@@
//
//     int SelectTestApiKeyDuplicateCount(String key); // 변경전 - RepositoryCustom 완료 @@@@
//
//     HashMap<String, Object> SelectApiKeyByCompanyIdx(HashMap<String, Object> paramMap); // 변경전 - RepositoryCustom 완료 @@@@
//
//     int SelectApiKeyDuplicateCount(String key); // 변경전 - RepositoryCustom 완료 @@@@
//
//     List<HashMap<String, Object>> SelectTestApiKeyExpiredList(HashMap<String, Object> paramMap); // 변경전 - RepositoryCustom 완료 @@@@
//
//     void UpdateBlockKey(int companyIdx);
//
//     void UpdateTestKeyExpire(int companyIdx);
//
//     void DeleteApiKeyByCompanyIdx(int companyIdx);

}
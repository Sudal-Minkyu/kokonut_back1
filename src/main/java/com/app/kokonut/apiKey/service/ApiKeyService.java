package com.app.kokonut.apiKey.service;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.dto.ApiKeyListCountDto;
import com.app.kokonut.apiKey.dto.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.repository.ApiKeyRepository;
import com.app.kokonut.woody.log4j.DBLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Woody
 * Date : 2022-10-25
 * Remark :
 */
@Slf4j
@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    @Autowired
    public ApiKeyService(ApiKeyRepository apiKeyRepository){
        this.apiKeyRepository = apiKeyRepository;
    }

	private DBLogger dblogger = new DBLogger(ApiKeyService.class);

	/**
	 * Api Key 리스트
	 */
//	public List<HashMap<String, Object>> SelectApiKeyList(HashMap<String, Object> paramMap) {
//		return dao.SelectApiKeyList(paramMap);
//	}
    public List<ApiKeyListAndDetailDto> findByApiKeyList(HashMap<String, Object> paramMap) {
        log.info("findByApiKeyList 호출");
        return apiKeyRepository.findByApiKeyList(paramMap);
    }

	/**
	 * Api Key 리스트 Count
	 */
//	public int SelectApiKeyListCount(HashMap<String, Object> paramMap) {
//        return dao.SelectApiKeyListCount(paramMap);
//    }
    public ApiKeyListCountDto findByApiKeyListCount(HashMap<String, Object> paramMap) {
        log.info("findByApiKeyListCount 호출");
        return apiKeyRepository.findByApiKeyListCount(paramMap);
    }

//	/**
//	 * Api Key 상세보기
//	 * @param idx
//	 */
//	public HashMap<String, Object> SelectApiKeyByIdx(int idx) {
//		return dao.SelectApiKeyByIdx(idx);
//	}
    public ApiKeyListAndDetailDto findByApiKeyDetail(Integer idx) {
        log.info("findByApiKeyDetail 호출");
        return apiKeyRepository.findByApiKeyDetail(idx);
    }


//	/**
//	 * Api Key Insert
//	 */
//	public void InsertApiKey(HashMap<String, Object> paramMap) {
//		dao.InsertApiKey(paramMap);
//	}
//
//	/**
//	 * Api Key Update
//	 */
//	public void UpdateApiKey(HashMap<String, Object> paramMap) {
//		dao.UpdateApiKey(paramMap);
//	}
//
//	/**
//	 * Api Key 삭제
//	 * @param idx
//	 */
//	public void DeleteApiKeyByIdx(int idx) {
//		dao.DeleteApiKeyByIdx(idx);
//	}
//

    /**
     * ApiKey 조회
     * @param key
     * @return
     */
//    public HashMap<String, Object> SelectByKey(String key) {
//        return apiKeyRepository.SelectByKey(key);
//    }
     public ApiKeyKeyDto findByKey(String key) {
         log.info("findByKey 호출");
         return apiKeyRepository.findByKey(key);
     }

//	/**
//	 * Test Api Key 조회
//	 * @param companyIdx
//	 */
//	public HashMap<String, Object> SelectTestApiKeyByCompanyIdx(int companyIdx) {
//		HashMap<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("companyIdx", companyIdx);
//
//		return dao.SelectTestApiKeyByCompanyIdx(paramMap);
//	}
//
//	/**
//	 * Test Api Key 중복 조회
//	 * @param key
//	 */
//	public int SelectTestApiKeyDuplicateCount(String key) {
//		return dao.SelectTestApiKeyDuplicateCount(key);
//	}
//
//	/**
//	 * 일반 Api Key 조회
//	 * @param CompanyIdx
//	 */
//	public HashMap<String, Object> SelectApiKeyByCompanyIdx(int companyIdx) {
//		HashMap<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("companyIdx", companyIdx);
//
//		return dao.SelectApiKeyByCompanyIdx(paramMap);
//	}
//
//	/**
//	 * Api Key 중복 조회
//	 * @param key
//	 */
//	public int SelectApiKeyDuplicateCount(String key) {
//		return dao.SelectApiKeyDuplicateCount(key);
//	}
//
//
//	/**
//	 * 만료 예정인 Test API Key 리스트
//	 */
//	public List<HashMap<String, Object>> SelectTestApiKeyExpiredList(HashMap<String, Object> paramMap) {
//		return dao.SelectTestApiKeyExpiredList(paramMap);
//	}
//
//	public static String keyGenerate(final int keyLen) throws NoSuchAlgorithmException {
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(keyLen);
//        SecretKey secretKey = keyGen.generateKey();
//        byte[] encoded = secretKey.getEncoded();
//        return DatatypeConverter.printHexBinary(encoded).toLowerCase();
//    }
//
//
//	/**
//	 * api key block 처리 - 결제 취소 시 사용
//	 */
//	public void UpdateBlockKey(int companyIdx) {
//		dao.UpdateBlockKey(companyIdx);
//	}
//
//	/*
//	 * 사용중인 TEST API KEY가 존재한다면 만료처리
//	 */
//	public void UpdateTestKeyExpire(int companyIdx) {
//		dao.UpdateTestKeyExpire(companyIdx);
//	}

//	/**
//	 * API KEY BLOCK, Send MAIL
//	 * @param companyIdx - 회사IDX
//	 */
//	public void BlockApiByCompanyIdx(int companyIdx) {
//		HashMap<String, Object> apiMap = new HashMap<String, Object>();
//		apiMap.put("companyIdx", companyIdx);
//		apiMap.put("useYn", "Y");
//		List<HashMap<String, Object>> apiKeyList = SelectApiKeyList(apiMap);
//		for(HashMap<String, Object> apiKey : apiKeyList) {
//			final String LOG_HEADER = "[kokonut Api Block]";
//			dblogger.save(DBLogger.LEVEL.INFO, Integer.parseInt(apiKey.get("IDX").toString()), CommonUtil.clientIp(), DBLogger.TYPE.DELETE, LOG_HEADER, "delete");
//		}
//		UpdateBlockKey(companyIdx);
//	}

}

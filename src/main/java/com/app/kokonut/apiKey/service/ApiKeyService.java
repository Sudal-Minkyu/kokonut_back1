package com.app.kokonut.apiKey.service;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.dto.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.dto.TestApiKeyExpiredListDto;
import com.app.kokonut.apiKey.entity.ApiKey;
import com.app.kokonut.apiKey.repository.ApiKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Calendar;
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

//	private DBLogger dblogger = new DBLogger(ApiKeyService.class);



    /**
     * Api Key Insert
     */
//	public void InsertApiKey(HashMap<String, Object> paramMap) {
//		dao.InsertApiKey(paramMap);
//	}
    /** JPA save()로 재구성 : InsertApiKey -> 변경후
     * 원래 받는 데이터 -> HashMap<String,Object> paramMap 형식
     * param
     * - Integer adminIdx, Integer companyIdx, String registerName, Integer type, Integer state
     * - String key
     */
    @Transactional
    public Integer InsertApiKey(Integer adminIdx, Integer companyIdx, String registerName, Integer type, Integer state,
                             String key) {
        log.info("InsertApiKey 호출");

        Date systemDate = new Date(System.currentTimeMillis());
        log.info("현재 날짜 : "+systemDate);

        ApiKey apiKey = new ApiKey();
        apiKey.setAdminIdx(adminIdx);
        apiKey.setCompanyIdx(companyIdx);
        apiKey.setRegisterName(registerName);
        apiKey.setType(type);
        apiKey.setState(state);
        apiKey.setUseAccumulate(1);
        apiKey.setKey(key);
        apiKey.setRegdate(systemDate);
        apiKey.setUseYn("Y");

        if(type != null && type.equals(2)){
            // 현재 LocalDate에서 14일후인 날짜계산
            Calendar c = Calendar.getInstance();
            c.setTime(systemDate);
            c.add(Calendar.DATE, 14);

            Date fourteenDayAfter = new Date(c.getTimeInMillis());
            log.info("14일후 날짜 : "+fourteenDayAfter);

            apiKey.setValidityStart(systemDate);
            apiKey.setValidityEnd(fourteenDayAfter);
        }

        return apiKeyRepository.save(apiKey).getIdx();
    }

    /**
     * Api Key Update
     */
//	public void UpdateApiKey(HashMap<String, Object> paramMap) {
//		dao.UpdateApiKey(paramMap);
//	}
    /** JPA save()로 재구성 : UpdateApiKey -> 변경후
     * 원래 받는 데이터 -> HashMap<String,Object> paramMap 형식
     * param
     * - Integer idx, String useYn, String reason, Integer modifierIdx, String modifierName
     */
    @Transactional
    public void UpdateApiKey(Integer idx, String useYn, String reason, Integer modifierIdx, String modifierName) {
        log.info("UpdateApiKey 호출");

        ApiKey apiKey = apiKeyRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ApiKey' 입니다."));

        Date systemDate = new Date(System.currentTimeMillis());
        log.info("현재 날짜 : "+systemDate);

        apiKey.setUseYn(useYn);
        apiKey.setReason(reason);

        apiKey.setModifierIdx(modifierIdx);
        apiKey.setModifierName(modifierName);
        apiKey.setModifyDate(systemDate);

        apiKeyRepository.save(apiKey);
    }

    /**
     * Api Key 삭제
     * @param idx
     */
//	public void DeleteApiKeyByIdx(int idx) {
//		dao.DeleteApiKeyByIdx(idx);
//	}
    /** JPA delete()로 재구성 : DeleteApiKeyByIdx -> 변경후
     * 원래 받는 데이터 -> int idx 형식
     * param
     * - Integer idx
     */
    @Transactional
    public void DeleteApiKeyByIdx(Integer idx) {
        log.info("DeleteApiKeyByIdx 호출");

        ApiKey apiKey = apiKeyRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ApiKey' 입니다."));

        apiKeyRepository.delete(apiKey);
    }

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
    public Long findByApiKeyListCount(HashMap<String, Object> paramMap) {
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

	/**
	 * Test Api Key 조회
	 * @param companyIdx
	 */
//	public HashMap<String, Object> SelectTestApiKeyByCompanyIdx(int companyIdx) {
//		HashMap<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("companyIdx", companyIdx);
//
//		return dao.SelectTestApiKeyByCompanyIdx(paramMap);
//	}
    public ApiKeyListAndDetailDto findByTestApiKeyByCompanyIdx(Integer companyIdx) {
        log.info("findByTestApiKeyByCompanyIdx 호출");
        return apiKeyRepository.findByTestApiKeyByCompanyIdx(companyIdx, 2);
    }

	/**
	 * Test Api Key 중복 조회
	 * @param key
	 */
//	public int SelectTestApiKeyDuplicateCount(String key) {
//		return dao.SelectTestApiKeyDuplicateCount(key);
//	}
    public Long findByTestApiKeyDuplicateCount(String key) {
        log.info("findByTestApiKeyDuplicateCount 호출");
        return apiKeyRepository.findByTestApiKeyDuplicateCount(key, 2);
    }

	/**
	 * 일반 Api Key 조회
	 * @param companyIdx
	 */
//	public HashMap<String, Object> SelectApiKeyByCompanyIdx(int companyIdx) {
//		HashMap<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("companyIdx", companyIdx);
//
//		return dao.SelectApiKeyByCompanyIdx(paramMap);
//	}
    public ApiKeyListAndDetailDto findByApiKeyByCompanyIdx(Integer companyIdx) {
        log.info("findByApiKeyByCompanyIdx 호출");
        return apiKeyRepository.findByApiKeyByCompanyIdx(companyIdx, 1, "Y");
    }

	/**
	 * Api Key 중복 조회
	 * @param key
	 */
//	public int SelectApiKeyDuplicateCount(String key) {
//		return dao.SelectApiKeyDuplicateCount(key);
//	}
    public Long findByApiKeyDuplicateCount(String key) {
        log.info("findByApiKeyDuplicateCount 호출");
        return apiKeyRepository.findByApiKeyDuplicateCount(key, 1);
    }

	/**
	 * 만료 예정인 Test API Key 리스트
	 */
//	public List<HashMap<String, Object>> SelectTestApiKeyExpiredList(HashMap<String, Object> paramMap) {
//		return dao.SelectTestApiKeyExpiredList(paramMap);
//	}
    public List<TestApiKeyExpiredListDto> findByTestApiKeyExpiredList(HashMap<String, Object> paramMap) {
        log.info("findByTestApiKeyExpiredList 호출");
        return apiKeyRepository.findByTestApiKeyExpiredList(paramMap, 2);
    }

    // 이게뭔지 검토필요... keyGenerate인거보니까 순차적으로 값 올려주는 메서드인듯?
	public static String keyGenerate(final int keyLen) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keyLen);
        SecretKey secretKey = keyGen.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return DatatypeConverter.printHexBinary(encoded).toLowerCase();
    }


	/**
	 * api key block 처리 - 결제 취소 시 사용
	 */
//	public void UpdateBlockKey(int companyIdx) {
//		dao.UpdateBlockKey(companyIdx);
//	}

	/*
	 * 사용중인 TEST API KEY가 존재한다면 만료처리
	 */
//	public void UpdateTestKeyExpire(int companyIdx) {
//		dao.UpdateTestKeyExpire(companyIdx);
//	}

	/**
	 * API KEY BLOCK, Send MAIL
	 * @param companyIdx - 회사IDX
	 */
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

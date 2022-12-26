package com.app.kokonut.company;

import com.app.kokonut.woody.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    /**
     * 회사정보 상세보기
     * @param idx
     */
    public ResponseEntity<Map<String,Object>> findByCompanyDetail(int idx) {

        log.info("findByCompanyDetail 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();




        data.put("", "");

        return ResponseEntity.ok(res.success(data));
    }

//    /**
//     * 회사정보 리스트
//     */
//    public List<HashMap<String, Object>> SelectCompanyList(HashMap<String, Object> paramMap) {
//        return dao.SelectCompanyList(paramMap);
//    }
//
//    /**
//     * 회사정보 리스트 Count
//     */
//    public int SelectCompanyListCount(HashMap<String, Object> paramMap) {
//        return dao.SelectCompanyListCount(paramMap);
//    }
//
//    /**
//     * 회사 암호화 키 조회
//     * @param companyIdx
//     * @return 암호화 키
//     * @throws Exception
//     */
//    public String SelectCompanyEncryptKey(Integer companyIdx) throws Exception {
//        HashMap<String, Object> data = SelectCompanyByIdx(companyIdx);
//
//        if(data == null) {
//            throw new Exception("not found company info.");
//        }
//
//        if(!data.containsKey("ENCRYPT_TEXT")) {
//            throw new Exception("not found company encrypt text");
//        }
//
//        if(!data.containsKey("DATA_KEY")) {
//            throw new Exception("not found company data key");
//        }
//
//        String encrpyText = data.get("ENCRYPT_TEXT").toString();
//        String dataKey = data.get("DATA_KEY").toString();
//
//        HashMap<String, Object> decryptedKeyInfo = awsKmsService.decrypt(encrpyText, dataKey);
//
//        /* 복호화 후 키 업데이트 처리 */
//        String decryptText = decryptedKeyInfo.get("decryptText").toString();
//        HashMap<String, Object> enc = awsKmsService.encrypt(decryptText);
//        if(enc.get("errorMsg").equals("")) {
//            enc.put("businessNumber", data.get("BUSINESS_NUMBER").toString());
//            dao.UpdateEncryptOfCompany(enc);
//        }
//
//        return decryptedKeyInfo.get("decryptText").toString();
//    }
//
//    /**
//     * 마스터 계정 승인 시 _user 스키마에 테이블 생성과 동시에
//     * KMS를 통해 암/복호화에 사용할 키를 생성한다.
//     */
//    public void UpdateEncryptOfCompany(HashMap<String, Object> paramMap) {
//        dao.UpdateEncryptOfCompany(paramMap);
//    }
//
//    /**
//     * 정기결제 정보 업데이트 처리
//     */
//    public void UpdatePaymentInfo(HashMap<String, Object> paramMap) {
//        dao.UpdatePaymentInfo(paramMap);
//    }
//
//    /**
//     * 결제 정보 취소 처리
//     */
//    public void UpdatePaymentCancel(HashMap<String, Object> paramMap) {
//        dao.UpdatePaymentCancel(paramMap);
//    }
//
//    /**
//     * CUSTOM_UID 조회
//     */
//    public String SelectCustomUid(HashMap<String, Object> paramMap) {
//        return dao.SelectCustomUid(paramMap);
//    }
//
//    /**
//     * 자동 결제 후, 결제 정보 저장
//     * @param companyIdx 회사IDX
//     * @param validEnd 회원권종료일자
//     * @param payRequestUid 결제요청UID
//     * @throws ParseException
//     */
//    public void UpdatePaymentAutoInfo(int companyIdx, String validEnd, String payRequestUid) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
//
//        HashMap<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("isAutoPay", 4);
//        paramMap.put("companyIdx", companyIdx);
//        Date today = new Date();
//        String validStart = sdf.format(today);
//        paramMap.put("validStart", validStart);
//
//        Date validEndDate = sdf.parse(validEnd);
//        Calendar tmpCal = Calendar.getInstance();
//        tmpCal = Calendar.getInstance();
//        tmpCal.setTime(validEndDate);
//        tmpCal.add(Calendar.MONTH, 1);
//        validEnd = sdf2.format(tmpCal.getTime())+"-05 23:59:59";
//        paramMap.put("validEnd", validEnd);
//
//
//        dao.UpdatePaymentAutoInfo(paramMap);
//    }
//
//    /**
//     * 회사정보 조회
//     * @param name
//     */
//    public HashMap<String, Object> SelectCompanyByName(String companyName) {
//        return dao.SelectCompanyByName(companyName);
//    }
//
//    /**
//     * Kokonut 암호화 키 조회
//     * @return 암호화 키
//     * @throws Exception
//     */
//    public String SelectKokonutEncryptKey() throws Exception {
//        HashMap<String, Object> data = SelectCompanyByName("contactKokonut");
//
//        if(data == null) {
//            throw new Exception("not found company info.");
//        }
//
//        if(!data.containsKey("ENCRYPT_TEXT")) {
//            throw new Exception("not found company encrypt text");
//        }
//
//        if(!data.containsKey("DATA_KEY")) {
//            throw new Exception("not found company data key");
//        }
//
//        String encrpyText = data.get("ENCRYPT_TEXT").toString();
//        String dataKey = data.get("DATA_KEY").toString();
//
//        /* 암호화키 복호화 처리*/
//        HashMap<String, Object> decryptedKeyInfo = awsKmsService.decrypt(encrpyText, dataKey);
//
//        /* 복호화 후 키 업데이트 처리 */
//        String decryptText = decryptedKeyInfo.get("decryptText").toString();
//        HashMap<String, Object> enc = awsKmsService.encrypt(decryptText);
//        if(enc.get("errorMsg").equals("")) {
//            enc.put("businessNumber", data.get("BUSINESS_NUMBER").toString());
//            dao.UpdateEncryptOfCompany(enc);
//        }
//
//        return decryptText;
//    }
//
//    /*
//     * 누적 휴면 회원 값 업데이트. 휴면 계정으로 전환된 회원의 누적 값을 관리한다.
//     */
//    public void UpdateDormantAccumulate(int companyIdx, int dormantCount) {
//        HashMap<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("companyIdx", companyIdx);
//        paramMap.put("dormantCount", dormantCount);
//        dao.UpdateDormantAccumulate(paramMap);
//    }
//
//    /**
//     * 강제 해제
//     */
//    public void UpdateStopService(int amount, int companyIdx) {
//        HashMap<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("amount", amount);
//        paramMap.put("companyIdx", companyIdx);
//        dao.UpdateStopService(paramMap);
//    }
//
//    /**
//     * 인원초과 체크
//     */
//    public boolean CheckUserCount(int companyIdx) {
//
//        HashMap<String, Object> company = SelectCompanyByIdx(companyIdx);
//        String businessNumber = company.get("BUSINESS_NUMBER").toString();
//        String isValid = company.get("IS_VALID").toString();
//
//        HashMap<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("companyIdx", companyIdx);
//
//        HashMap<String, Object> testApiKey = apiKeydao.SelectTestApiKeyByCompanyIdx(paramMap);
//        String apiKeyValid = "";
//        String useYn = "";
//
//        if(testApiKey != null) {
//            apiKeyValid = testApiKey.get("IS_VALID").toString();
//            useYn = testApiKey.get("USE_YN").toString();
//
//            if("N".equals(isValid) && useYn.equals("N") && apiKeyValid.equals("N")) {
//                return false;
//            }
//        }
//
//        HashMap<String, Object> apiKey = apiKeydao.SelectApiKeyByCompanyIdx(paramMap);
//        if(apiKey != null) {
//            apiKeyValid = apiKey.get("IS_VALID").toString();
//            useYn = apiKey.get("USE_YN").toString();
//
//            String service = "";
//            if("N".equals(isValid) && useYn.equals("Y") && apiKeyValid.equals("Y")) {
//                service = "STANDARD";
//            } else {
//                service = company.get("SERVICE").toString();
//            }
//
//            if("BASIC".equals(service)) {
//                paramMap.put("TABLE_NAME", businessNumber);
//                int userTotalCount = dynamicUserDao.SelectCountAll(paramMap) + dynamicDormantUserDao.SelectCountAll(paramMap);
//                if(userTotalCount >= 5000) {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }
//
//
//    /**
//     * 이용기간 기간 3일 연장
//     * @param companyIdx
//     */
//    public void UpdateValidEndThreeDays(int idx) {
//        dao.UpdateValidEndThreeDays(idx);
//    }
//
//    public List<HashMap<String, Object>> SelectCompanySendMessageList(HashMap<String, Object> paramMap) {
//        return dao.SelectCompanySendMessageList(paramMap);
//    }
//
//    public int SelectCompanySendMessageListCount(HashMap<String, Object> paramMap) {
//        return dao.SelectCompanySendMessageListCount(paramMap);
//    }






}

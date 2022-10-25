package com.app.kokonut.woody.restcontroller;

import com.app.kokonut.woody.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark : Kokonut Member RestController
 */
@Slf4j
@RestController
@RequestMapping("/api/member")
public class MemberRestController {

    private final MemberService memberService;

    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     *  서비스 > API 관리 > API key 발급
     */
//    @PostMapping(value = "/issue")
//    @ResponseBody
//    public HashMap<String, Object> issue(@RequestBody HashMap<String,Object> paramMap, @AuthorizedUser AuthUser authUser) {
//        HashMap<String, Object> returnMap = new HashMap<String, Object>();
//        returnMap.put("isSuccess", "false");
//        returnMap.put("errorCode", "ERROR_UNKNOWN");
//
//        do {
//
//            int adminIdx = authUser.getUser().getIdx();
//            int companyIdx = authUser.getUser().getCompanyIdx();
//            String registerName = authUser.getUser().getName();
//            paramMap.put("adminIdx", adminIdx);
//            paramMap.put("companyIdx", companyIdx);
//            paramMap.put("registerName", registerName);
//            paramMap.put("type", "1");
//            paramMap.put("useAccumulate", 1);
//            paramMap.put("state", 1);

//            /* 기존에 등록한 키가 있는 지 확인 */
//            HashMap<String, Object> apiKey =  apiKeyService.SelectApiKeyByCompanyIdx(companyIdx);
//            if(apiKey != null && !apiKey.isEmpty()) {
//                returnMap.put("errorCode", "ERROR_HAVE_APIKEY");
//                break;
//            }
//
//            try {
//                String key = ApiKeyService.keyGenerate(128);
//                while(true) {
//                    int keyCount = apiKeyService.SelectApiKeyDuplicateCount(key);
//                    if(keyCount < 1) {
//                        break;
//                    } else {
//                        key =  ApiKeyService.keyGenerate(128);
//                    }
//                }
//
//                paramMap.put("key", key);
//            } catch (NoSuchAlgorithmException e) {
//                logger.error(e.getMessage());
//            }
//
//            apiKeyService.InsertApiKey(paramMap);
//
//
//            final String LOG_HEADER = "[kokonut Api]";
//            dblogger.save(DBLogger.LEVEL.INFO, Integer.parseInt(paramMap.get("idx").toString()), CommonUtil.clientIp(), DBLogger.TYPE.CREATE, LOG_HEADER, "create");

//            returnMap.put("isSuccess", "true");
//            returnMap.put("errorCode", "ERROR_SUCCESS");
//
//        } while(false);
//
//        return returnMap;
//    }


}

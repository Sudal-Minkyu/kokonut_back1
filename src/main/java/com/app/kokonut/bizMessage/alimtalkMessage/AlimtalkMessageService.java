package com.app.kokonut.bizMessage.alimtalkMessage;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.auth.jwt.util.SecurityUtil;
import com.app.kokonut.bizMessage.alimtalkMessage.dto.AlimtalkMessageInfoListDto;
import com.app.kokonut.bizMessage.alimtalkMessage.dto.AlimtalkMessageListDto;
import com.app.kokonut.bizMessage.alimtalkMessage.dto.AlimtalkMessageSearchDto;
import com.app.kokonut.bizMessage.alimtalkMessage.entity.AlimtalkMessage;
import com.app.kokonut.bizMessage.navercloud.NaverCloudPlatformResultDto;
import com.app.kokonut.bizMessage.navercloud.NaverCloudPlatformService;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author Woody
 * Date : 2022-12-19
 * Time :
 * Remark : AlimtalkMessage Service
 */
@Slf4j
@Service
public class AlimtalkMessageService {

    private final NaverCloudPlatformService naverCloudPlatformService;

    private final AdminRepository adminRepository;

    private final AlimtalkMessageRepository alimtalkMessageRepository;

    @Autowired
    public AlimtalkMessageService(NaverCloudPlatformService naverCloudPlatformService, AdminRepository adminRepository, AlimtalkMessageRepository alimtalkMessageRepository) {
        this.naverCloudPlatformService = naverCloudPlatformService;
        this.adminRepository = adminRepository;
        this.alimtalkMessageRepository = alimtalkMessageRepository;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public ResponseEntity<Map<String, Object>> alimTalkMessageList(AlimtalkMessageSearchDto alimtalkMessageSearchDto, Pageable pageable) {
        log.info("alimTalkMessageList 조회");

        AjaxResponse res = new AjaxResponse();

        String email = SecurityUtil.getCurrentUserEmail();

        // 해당 이메일을 통해 회사 IDX 조회
        int companyIdx = adminRepository.findByCompanyInfo(email).getCompanyIdx();
        List<AlimtalkMessageInfoListDto> alimTalkMessageList = alimtalkMessageRepository.findByAlimtalkMessageInfoList(companyIdx, "1");

        List<AlimtalkMessage> alimtalkMessageList = new ArrayList<>();
        for(int i = 0; i < alimTalkMessageList.size(); i++) {

            NaverCloudPlatformResultDto result;

            int idx = alimTalkMessageList.get(i).getIdx();
            String requestId = alimTalkMessageList.get(i).getRequestId();
            String transmitType = alimTalkMessageList.get(i).getTransmitType();
            String status = alimTalkMessageList.get(i).getStatus();
            String updateSatus;

            log.info("status 현재 상태 : "+status);

            if(transmitType.equals("reservation")) {
                log.info("예약발송 조회");
                result = naverCloudPlatformService.getReserveState(requestId, NaverCloudPlatformService.typeAlimTalk);
                System.out.println("reservation result : " + result);
            } else {
                log.info("즉시발송 조회");
                result = naverCloudPlatformService.getMessages(requestId, NaverCloudPlatformService.typeAlimTalk);
                System.out.println("immediate result : " + result);
            }

            if(result.getResultCode().equals(200)) {
                JsonObject obj = (JsonObject) JsonParser.parseString(result.getResultText());

                Gson gson = new Gson();
                HashMap<String, Object> map = new HashMap<>();
                map = (HashMap<String, Object>)gson.fromJson(obj, map.getClass());

                if(transmitType.equals("reservation")) {
                    updateSatus = map.containsKey("reserveStatus")?map.get("reserveStatus").toString() : "";
                } else {
                    updateSatus = map.containsKey("statusName") ? map.get("statusName").toString() : "";
                }

                if(!updateSatus.equals("")) {
                    Optional<AlimtalkMessage> optionalAlimtalkMessage = alimtalkMessageRepository.findById(idx);
                    if(optionalAlimtalkMessage.isPresent()){
                        optionalAlimtalkMessage.get().setStatus(updateSatus);
                        alimtalkMessageList.add(optionalAlimtalkMessage.get());
                    } else {
                        log.error("해당 알림톡 메세지를 찾을 수 없습니다.");
                        return ResponseEntity.ok(res.fail(ResponseErrorCode.KO023.getCode(), ResponseErrorCode.KO023.getDesc()));
                    }
                }
            }
        }

        // 전체 업데이트
        alimtalkMessageRepository.saveAll(alimtalkMessageList);

        Page<AlimtalkMessageListDto> alimtalkMessageListDtos = alimtalkMessageRepository.findByAlimtalkMessagePage(alimtalkMessageSearchDto, companyIdx, pageable);

        return ResponseEntity.ok(res.ResponseEntityPage(alimtalkMessageListDtos));
    }


    public ResponseEntity<Map<String, Object>> alimTalkMessageTemplateList(String channelId, String templateCode) {
        log.info("alimTalkMessageTemplateList 조회");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String email = SecurityUtil.getCurrentUserEmail();

        log.info("channelId : "+channelId);
        log.info("templateCode : "+templateCode);



        data.put("data", "ㅇㅇㅇ");

        return ResponseEntity.ok(res.success(data));
    }

//        List<HashMap<String, Object>> templates = new ArrayList<>();
//        List<HashMap<String, Object>> template = new ArrayList<>();
//        try{
//            String channelId = paramMap.get("channelId").toString();
//            int companyIdx = authUser.getUser().getCompanyIdx();
//            paramMap.put("companyIdx", companyIdx);
//
//            List<HashMap<String, Object>> templateList = alimTalkMessageService.SelectAlimTalkTemplateList(paramMap);
//            for(int j = 0; j < templateList.size(); j++) {
//
//                String templateCode = "";
//                if(paramMap.containsKey("templateCode")) {
//                    templateCode = paramMap.get("templateCode").toString();
//                } else {
//                    templateCode = templateList.get(j).get("TEMPLATE_CODE").toString();
//                }
//
//                HashMap<String, Object> result = naverCloudPlatformService.getTemplates(channelId, templateCode, "");
//
//                if(result.get("responseCode").toString().equals("200")) {
//                    ObjectMapper mapper = new ObjectMapper();
//                    template = mapper.readValue(result.get("response").toString(), new TypeReference<List<HashMap<String, Object>>>(){});
//
//                    String messageType = templateList.get(j).get("MESSAGE_TYPE").toString();
//                    template.get(0).put("messageType", messageType);
//
//                    if("EX".equals(messageType) || "MI".equals(messageType) ) {
//                        String extraContent = templateList.get(j).get("EXTRA_CONTENT").toString();
//                        template.get(0).put("extraContent", extraContent);
//                    }
//                    if("AD".equals(messageType) || "MI".equals(messageType) ) {
//                        String adContent = templateList.get(j).get("AD_CONTENT").toString();
//                        template.get(0).put("adContent", adContent);
//                    }
//
//                    String emphasizeType = templateList.get(j).get("EMPHASIZE_TYPE").toString();
//                    template.get(0).put("emphasizeType", emphasizeType);
//
//                    if("TEXT".equals(emphasizeType)) {
//                        String emphasizeTitle = templateList.get(j).get("EMPHASIZE_TITLE").toString();
//                        String emphasizeSubTitle = templateList.get(j).get("EMPHASIZE_SUB_TITLE").toString();
//                        template.get(0).put("emphasizeTitle", emphasizeTitle);
//                        template.get(0).put("emphasizeSubTitle", emphasizeSubTitle);
//                    }
//
//                    templates.add(template.get(0));
//                }
//            }
//
//            returnMap.put("templates", templates);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        returnMap.put("isSuccess", "true");
//        returnMap.put("errorCode", "ERROR_SUCCESS");
//
//    } while(false);
//
//        return returnMap;

//    /***
//     * 알림톡 메시지 발송 요청
//     */
//    @RequestMapping(value = "/postMessages", method = RequestMethod.POST)
//    @ResponseBody
//    public HashMap<String, Object> postMessages(@RequestBody HashMap<String,Object> paramMap, @AuthorizedUser AuthUser authUser) {
//        HashMap<String, Object> returnMap = new HashMap<String, Object>();
//        returnMap.put("isSuccess", "false");
//        returnMap.put("errorCode", "ERROR_UNKNOWN");
//
//        String email = authUser.getUser().getEmail();
//        if(email.equals("test@kokonut.me")){
//            System.out.println("체험하기모드는 할 수 없습니다.");
//            returnMap.put("errorCode", "ERROR_EXPERIENCE");
//            return returnMap;
//        }
//
//        do {
//            if(!paramMap.containsKey("templateCode")) {
//                returnMap.put("errorCode", "ERROR_NOT_FOUND_TEMPLATE_CODE");
//                break;
//            }
//            if(!paramMap.containsKey("plusFriendId")) {
//                returnMap.put("errorCode", "ERROR_NOT_FOUND_PLUS_FRIEND_ID");
//                break;
//            }
//            if(!paramMap.containsKey("recipients")) {
//                returnMap.put("errorCode", "ERROR_NOT_FOUND_RECIPIENTS");
//                break;
//            }
//            if(!paramMap.containsKey("content")) {
//                returnMap.put("errorCode", "ERROR_NOT_FOUND_CONTENT");
//                break;
//            }
//
//            HashMap<String, Object> result = naverCloudPlatformService.postMessages(paramMap);
//            if(result.get("responseCode").toString().equals("202")) {
//                // 채널 등록 정보 INSERT
//                int companyIdx = authUser.getUser().getCompanyIdx();
//                paramMap.put("companyIdx", companyIdx);
//                HashMap<String,Object> response = Utils.convertJSONstringToMap(result.get("response").toString());
//                paramMap.put("requestId", response.get("requestId").toString());
//                paramMap.put("channelId", paramMap.get("plusFriendId"));
//                paramMap.put("transmitType", paramMap.get("transmitDateType"));
//
//                if(paramMap.get("transmitDateType").toString().equals("reservation"))
//                    paramMap.put("reservationDate", paramMap.get("reservationDate"));
//
//                alimTalkMessageService.InsertAlimTalkMessage(paramMap);
//
//                alimTalkMessageService.InsertAlimTalkMessageRecipient(paramMap);
//                returnMap.put("isSuccess", "true");
//                returnMap.put("errorCode", "ERROR_SUCCESS");
//            } else {
//                returnMap.put("errorCode", result.get("responseCode").toString());
//            }
//
//            returnMap.put("result", result);
//
//        } while(false);
//
//        return returnMap;
//    }

}

package com.app.kokonut.bizMessage.alimtalkMessage;

import com.app.kokonut.bizMessage.alimtalkMessage.dto.AlimtalkMessageSearchDto;
import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkTemplateSearchDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Woody
 * Date : 2022-12-19
 * Time :
 * Remark : AlimtalkMessage RestController
 */
@Slf4j
@RestController
@RequestMapping("/api/AlimtalkMessage")
public class AlimtalkMessageRestController {

    private final AlimtalkMessageService alimtalkMessageService;

    @Autowired
    public AlimtalkMessageRestController(AlimtalkMessageService alimtalkMessageService) {
        this.alimtalkMessageService = alimtalkMessageService;
    }

    // 알림톰 메세지 리스트 조회
    @PostMapping(value = "/alimTalkMessageList")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> alimTalkMessageList(@RequestBody AlimtalkMessageSearchDto alimtalkTemplateSearchDto, Pageable pageable) {
        return alimtalkMessageService.alimTalkMessageList(alimtalkTemplateSearchDto, pageable);
    }

    // 알림톡 메세지 발송요청의 템플릿 리스트 조회 -> 선택한 채널ID의 템플릿 코드리스트를 반환한다.
    @PostMapping(value = "/alimTalkMessageTemplateList")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> alimTalkMessageTemplateList(@RequestParam(name="channelId", defaultValue = "") String channelId,
                                                                          @RequestParam(name="templateCode", defaultValue = "") String templateCode) {
        return alimtalkMessageService.alimTalkMessageTemplateList(channelId, templateCode);
    }

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

package com.app.kokonut.refactor.personalInfoProvision.restcontroller;

import com.app.kokonut.refactor.personalInfoProvision.dto.PersonalInfoProvisionListDto;
import com.app.kokonut.refactor.personalInfoProvision.dto.PersonalInfoProvisionSaveDto;
import com.app.kokonut.refactor.personalInfoProvision.dto.PersonalInfoProvisionSetDto;
import com.app.kokonut.refactor.personalInfoProvision.service.PersonalInfoProvisionService;
import com.app.kokonut.woody.common.AjaxResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/personal-info")
public class PersonalInfoProvisionRestController {

    private final PersonalInfoProvisionService personalInfoProvisionService;

    @Autowired
    public PersonalInfoProvisionRestController(PersonalInfoProvisionService personalInfoProvisionService){
        this.personalInfoProvisionService = personalInfoProvisionService;
    }

    // 기존 코코넛 메서드 : provisionList
    @PostMapping("/provision/list")
    @ApiOperation(value = "정보제공 목록 조회 API", notes = "" +
            "PersonalInfoProvision 리스트를 조회한다.")
    public ResponseEntity<Map<String,Object>> personalInfoProvisionList(@RequestBody PersonalInfoProvisionSetDto personalInfoProvisionSetDto){

        // 기존코드
//        DataTables dataTables = null;
//        List<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
//        int total = 0;
//
//        try{
//            HashMap<String, Object> searchMap = null;
//            if(paramMap.containsKey("searchData")){
//                searchMap = (HashMap<String, Object>) paramMap.get("searchData");
//                paramMap.putAll(searchMap);
//            }
//
//            paramMap.put("companyIdx", authUser.getUser().getCompanyIdx());
//
//            if(!paramMap.containsKey("state")) {
//                throw new Exception("not found state");
//            }
//
//            rows = personalInfoService.getProvisionList(paramMap);
//            total = personalInfoService.getProvisionListCount(paramMap);
//
//            dataTables = new DataTables(paramMap, rows, total);
//        }catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        return dataTables.getJsonString();

        log.info("PersonalInfoProvisionList 리스트 호출");

        log.info("@RequestBody personalInfoProvisionSetDto : "+personalInfoProvisionSetDto);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<PersonalInfoProvisionListDto> personalInfoProvisionListDtos
                = personalInfoProvisionService.findByProvisionList(personalInfoProvisionSetDto.getPersonalInfoProvisionMapperDto());
        Integer total = personalInfoProvisionListDtos.size();

        log.info("personalInfoProvisionListDtos : "+personalInfoProvisionListDtos);
        log.info("total : "+total);

        data.put("personalInfoProvisionListDtos",personalInfoProvisionListDtos);
        data.put("total",total);

        return ResponseEntity.ok(res.success(data));
    }


//    @RequestMapping(value = "/provision/list/for-agree", method = RequestMethod.POST)
//    @ResponseBody
    @PostMapping("/provision/list/for-agree")
    @ApiOperation(value = "잘 모르겠음. 주석안되있는 API", notes = "" +
            " * 상태 \n" +
            " * 1: 수집중: 정보제공일 이전\n" +
            " * 2: 수집완료: 정보제공일 부터 정보제공 만료일 까지\n" +
            " * 3: 기간만료: 정보제공 만료일 이후\n" +
            " */")
    public ResponseEntity<Map<String,Object>> provisionListForAgree(@RequestBody PersonalInfoProvisionSetDto personalInfoProvisionSetDto) {
//    public ResponseEntity<Map<String,Object>> provisionListForAgree(@RequestBody PersonalInfoProvisionSetDto personalInfoProvisionSetDto, @AuthorizedUser AuthUser authUser) {
//        DataTables dataTables = null;
//        List<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
//        int total = 0;

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        try{

//            HashMap<String, Object> searchMap = null;
//            if(paramMap.containsKey("searchData")){
//                searchMap = (HashMap<String, Object>) paramMap.get("searchData");
//                paramMap.putAll(searchMap);
//            }
//
//            paramMap.put("companyIdx", authUser.getUser().getCompanyIdx());
            personalInfoProvisionSetDto.getPersonalInfoProvisionMapperDto().setCompanyIdx(13);

//            if(!paramMap.containsKey("state")) {
//                throw new Exception("not found state");
//            }
//
//            final Integer STATE = Integer.parseInt(paramMap.get("state").toString());
            final Integer STATE = personalInfoProvisionSetDto.getPersonalInfoProvisionMapperDto().getState();

            // 수집 필수 - 동의여부 별도 수집 조건 추가
            personalInfoProvisionSetDto.getPersonalInfoProvisionMapperDto().setAgreeYn("Y");
            personalInfoProvisionSetDto.getPersonalInfoProvisionMapperDto().setAgreeType(2);
//            paramMap.put("agreeYn", 'Y');
//            paramMap.put("agreeType", 2);

//            rows = personalInfoProvisionService.getProvisionList(paramMap);
//            total = rows.size();

            List<PersonalInfoProvisionListDto> personalInfoProvisionListDtos = personalInfoProvisionService.findByProvisionList(personalInfoProvisionSetDto.getPersonalInfoProvisionMapperDto());
            int total = personalInfoProvisionListDtos.size();

            /*
             * 상태
             * 1: 수집중: 정보제공일 이전
             * 2: 수집완료: 정보제공일 부터 정보제공 만료일 까지
             * 3: 기간만료: 정보제공 만료일 이후
             */

            // 상태값 계산
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//            for(int i = rows.size()-1; i >= 0; i--) {
//                HashMap<String, Object> row = rows.get(i);
//
//                Date startDate = sdf.parse(row.get("START_DATE").toString());
//                Date expDate = sdf.parse(row.get("EXP_DATE").toString());
//
//                int rowState = 0;
//
//                if(today.compareTo(startDate) < 0) {
//                    rowState = 1; // 수집중
//                }
//                else if(today.compareTo(startDate) >= 0 && today.compareTo(expDate) <= 0){
//                    rowState = 2; // 수집완료
//                }
//                else {
//                    rowState = 3; // 기간만료
//                }
//
//                if(STATE == 0 || (STATE != 0 && STATE == rowState)) {
//                    row.put("STATE", rowState);
//                }
//                else {
//                    rows.remove(i);
//                    total--;
//                }
//            }
            for(int i = total-1; i >= 0; i--) {
                PersonalInfoProvisionListDto row = personalInfoProvisionListDtos.get(i);

                Date startDate = sdf.parse(row.getStartDate().toString());
                Date expDate = sdf.parse(row.getExpDate().toString());

                int rowState = 0;

                if(today.compareTo(startDate) < 0) {
                    rowState = 1; // 수집중
                }
                else if(today.compareTo(startDate) >= 0 && today.compareTo(expDate) <= 0){
                    rowState = 2; // 수집완료
                }
                else {
                    rowState = 3; // 기간만료
                }

                if(STATE == 0 || STATE == rowState) {
                    row.setState(rowState);
                }
                else {
                    personalInfoProvisionListDtos.remove(i);
                    total--;
                }
            }

            data.put("personalInfoProvisionListDtos",personalInfoProvisionListDtos);
            data.put("total",total);

//            dataTables = new DataTables(paramMap, rows, total);

        }catch (Exception e) {
//            logger.error(e.getMessage());
            log.error("e : "+e);
        }

        return ResponseEntity.ok(res.success(data));
    }

//    @RequestMapping(value = "/provision/save", method = RequestMethod.POST)
//    @ResponseBody
    @PostMapping("/provision/save")
    @ApiOperation(value = "정보제공 저장 API", notes = "" +
            "PersonalInfoProvision 저장")
    public Map<String, Object> provisionSave(@ModelAttribute PersonalInfoProvisionSaveDto data, HttpServletRequest request){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        boolean success = false;
        String error = "UNKNOWN";

//        do {
////            KokonutUser user = authUser.getUser();
//            KokonutUser user = new KokonutUser();
//            user.setCompanyIdx(13);
//            user.setIdx(1);
//
//            data.setCompanyIdx(user.getCompanyIdx());
//            data.setAdminIdx(user.getIdx());
//
//            String number = null;
//            if((number = personalInfoProvisionService.saveProvision(data)) == null) {
//                error = "FAILED";
//                break;
//            }
//
//            // 별도수집인 경우 별도수집 대상 목록을 미리 저장한다.
//            if(data.getRecipientType() == 2 && (data.getType() == 1 || data.getType() == 2) && data.getAgreeYn() == 'Y' && data.getAgreeType() == 2) {
//                String tableName = dynamicUserService.SelectTableName(data.getCompanyIdx());
//                List<Map<String, Object>> userList = dynamicUserService.SelectUserListByTableName(tableName);
//
//                if(data.getTargetStatus().equals("ALL")) {
//                    List<String> idList = new ArrayList<String>();
//                    for(Map<String, Object> userInfo : userList) {
//                        String id = userInfo.get("ID").toString();
//                        idList.add(id);
//                    }
//
//                    personalInfoService.saveTempProvisionAgree(number, idList);
//                } else {
//                    List<String> idList = new ArrayList<String>();
//
//                    String[] userIdxList = data.getTargets().split(",");
//                    for(String userIdx : userIdxList) {
//                        for(int i = userList.size()-1; i >= 0; i--) {
//                            Map<String, Object> userInfo = userList.get(i);
//                            if(userInfo.get("IDX").toString().equals(userIdx)) {
//                                idList.add(userInfo.get("ID").toString());
//                                userList.remove(i);
//                                break;
//                            }
//                        }
//                    }
//
//                    personalInfoService.saveTempProvisionAgree(number, idList);
//                }
//            }
//
//            // 받는사람에게 이메일 전송
//            personalInfoService.sendEmailToRecipient(
//                    request,
//                    number,
//                    data.getRecipientEmail(),
//                    data.getStartDate(),
//                    data.getExpDate(),
//                    data.getPeriod());
//
//            success = true;
//        } while(false);
//
//        returnMap.put("success", success);
//        returnMap.put("errorCode", error);
        return returnMap;
    }













}
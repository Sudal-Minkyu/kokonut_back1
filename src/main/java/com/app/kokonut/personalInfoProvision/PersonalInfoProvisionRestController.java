package com.app.kokonut.personalInfoProvision;

import com.app.kokonut.personalInfoProvision.dtos.PersonalInfoProvisionListDto;
import com.app.kokonut.personalInfoProvision.dtos.PersonalInfoProvisionSaveDto;
import com.app.kokonut.personalInfoProvision.dtos.PersonalInfoProvisionSetDto;
import com.app.kokonut.common.AjaxResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Woody
 * Date : 2023-01-17
 * Remark :
 */
@Slf4j
@RestController
@RequestMapping("/v2/api/PersonalInfoProvision")
public class PersonalInfoProvisionRestController {

    private final PersonalInfoProvisionService personalInfoProvisionService;

    @Autowired
    public PersonalInfoProvisionRestController(PersonalInfoProvisionService personalInfoProvisionService){
        this.personalInfoProvisionService = personalInfoProvisionService;
    }

    // 기존 코코넛 메서드 : provisionList
    @PostMapping("/list")
    @ApiOperation(value = "정보제공 목록 조회 API", notes = "" +
            "PersonalInfoProvision 리스트를 조회한다.")
    public ResponseEntity<Map<String,Object>> personalInfoProvisionList(@RequestBody PersonalInfoProvisionSetDto personalInfoProvisionSetDto,
                                                                        Pageable pageable){

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
    @PostMapping("/for-agree")
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

    @PostMapping("/save")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "정보제공 저장 API", notes = "" +
            "PersonalInfoProvision 저장")
    public ResponseEntity<Map<String, Object>> provisionSave(@RequestBody PersonalInfoProvisionSaveDto personalInfoProvisionSaveDto){
//        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return personalInfoProvisionService.privisionSave(personalInfoProvisionSaveDto, "woody2@kokonut.me");
    }













}
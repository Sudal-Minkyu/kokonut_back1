package com.app.kokonut.personalInfoProvision.restcontroller;

import com.app.kokonut.personalInfoProvision.dto.PersonalInfoProvisionListDto;
import com.app.kokonut.personalInfoProvision.dto.PersonalInfoProvisionSetDto;
import com.app.kokonut.personalInfoProvision.service.PersonalInfoProvisionService;
import com.app.kokonut.woody.common.AjaxResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/PersonalInfo")
public class PersonalInfoProvisionRestController {

    private final PersonalInfoProvisionService personalInfoProvisionService;

    @Autowired
    public PersonalInfoProvisionRestController(PersonalInfoProvisionService personalInfoProvisionService){
        this.personalInfoProvisionService = personalInfoProvisionService;
    }

    @PostMapping("/provision/list")
    @ApiOperation(value = "정보제공 목록 조회 API", notes = "" +
            "PersonalInfoProvision 리스트 조회 테스트")
    public ResponseEntity<Map<String,Object>> PersonalInfoProvisionList(@RequestBody PersonalInfoProvisionSetDto personalInfoProvisionSetDto){

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

}
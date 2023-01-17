package com.app.kokonut.apiKey;

import com.app.kokonut.apiKey.dtos.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.dtos.ApiKeySetDto;
import com.app.kokonut.auth.jwt.SecurityUtil;
import com.app.kokonut.auth.jwt.dto.JwtFilterDto;
import com.app.kokonut.common.AjaxResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/api/ApiKey")
public class ApiKeyRestController {

//    private Logger logger = LoggerFactory.getLogger(ApiKeyRestController.class);

    private final ApiKeyService apiKeyService;

    @Autowired
    public ApiKeyRestController(ApiKeyService apiKeyService){
        this.apiKeyService = apiKeyService;
    }

    /**
     *  시스템 관리자 > API 관리 > API key 리스트
     */
    @PostMapping("list")
    @ApiOperation(value = "ApiKey 리스트 호출 API", notes = "" +
            "시스템 관리자 > API 관리 > API key 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> ApiKeyList(@RequestBody ApiKeySetDto apiKeySetDto){

        log.info("API key 리스트 호출");

        log.info("@RequestBody apiKeySetDto : "+apiKeySetDto);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

//        DataTables dataTables;

//        try{
//            HashMap<String, Object> searchMap;
//            searchMap = (HashMap<String, Object>) paramMap.get("searchData");

//            if(paramMap.containsKey("searchData")){
//                searchMap = (HashMap<String, Object>) paramMap.get("searchData");
//                paramMap.putAll(searchMap);
//            }

        List<ApiKeyListAndDetailDto> apiKeyListAndDetailDtos = apiKeyService.findByApiKeyList(apiKeySetDto.getApiKeyMapperDto());
        Long total = apiKeyService.findByApiKeyListCount(apiKeySetDto.getApiKeyMapperDto());
        log.info("apiKeyListAndDetailDtos : "+apiKeyListAndDetailDtos);
        log.info("total : "+total);

        data.put("apiKeyListAndDetailDtos",apiKeyListAndDetailDtos);
        data.put("total",total);

//            dataTables = new DataTables(apiKeyTestDto.getSearchData(), data, total);

//            return dataTables.getJsonString();
//        }
//        catch (Exception e) {
//            log.info("예외발생 : "+e);
//
//            return "아무일도없었다.";
//        }

        return ResponseEntity.ok(res.success(data));
    }

    /**
     * 서비스 > API 연동관리 > API key 연동관리
     */
    @PostMapping("/apiKeyManagement")
    @ApiOperation(value = "ApiKey 발급 내역 조회", notes = "서비스 > API 연동관리 > API key 연동관리")
    @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")
    public ResponseEntity<Map<String,Object>> apiKeyManagement(){
        log.info("ApiKey 발급 내역 조회 호출");
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return apiKeyService.apiKeyManagement(jwtFilterDto.getEmail(), jwtFilterDto.getRole());
    }

    /**
     * 서비스 > API 관리 > API key 발급내역
     */
    @PostMapping("/testIssue")
    @ApiOperation(value = "테스트 ApiKey 발급", notes = "서비스 > API 연동관리 > API key 연동관리")
    @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")
    public ResponseEntity<Map<String,Object>> testIssue(){
        log.info("테스트 ApiKey 발급");
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return apiKeyService.testIssue(jwtFilterDto.getEmail(), jwtFilterDto.getRole());
    }

    @PostMapping("/issue")
    @ApiOperation(value = "ApiKey 발급", notes = "서비스 > API 연동관리 > API key 연동관리, ApiKey 발급")
    @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")
    public ResponseEntity<Map<String,Object>> issue() throws NoSuchAlgorithmException {
        log.info("ApiKey 발급 호출");
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return apiKeyService.issue(jwtFilterDto.getEmail(), jwtFilterDto.getRole());
    }

    @PostMapping("/reIssue")
    @ApiOperation(value = "ApiKey 재발급", notes = "서비스 > API 연동관리 > API key 연동관리, ApiKey 재발급")
    @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")
    public ResponseEntity<Map<String,Object>> reIssue() throws NoSuchAlgorithmException {
        log.info("ApiKey 재발급 호출");
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return apiKeyService.reIssue(jwtFilterDto.getEmail(), jwtFilterDto.getRole());
    }

    @PostMapping("/modify")
    @ApiOperation(value = "ApiKey 수정", notes = "서비스 > API 연동관리 > API key 연동관리")
    @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")
    public ResponseEntity<Map<String,Object>> modify(@RequestParam(name="idx") Integer idx, @RequestParam(name="useYn") String useYn, @RequestParam(name="reason") String reason) {
        log.info("ApiKey 수정 호출");
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return apiKeyService.modify(idx, useYn, reason, jwtFilterDto.getEmail(), jwtFilterDto.getRole());
    }
}

package com.app.kokonut.apiKey.restcontroller;

import com.app.kokonut.apiKey.dto.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.repository.ApiKeyRepository;
import com.app.kokonut.apiKey.service.ApiKeyService;
import com.app.kokonut.woody.common.component.DataTables;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "")
@Validated
@RestController
@RequestMapping("/api/ApiKey")
public class ApiKeyRestController {

    private Logger logger = LoggerFactory.getLogger(ApiKeyRestController.class);

    private final ApiKeyService apiKeyService;

    @Autowired
    public ApiKeyRestController(ApiKeyService apiKeyService){
        this.apiKeyService = apiKeyService;
    }

    /**
     *  시스템 관리자 > API 관리 > API key 리스트
     */
    @SuppressWarnings("unchecked")
    @GetMapping("list")
    @ApiOperation(value = "ApiKey 리스트 호출 API", notes = "" +
            "시스템 관리자 > API 관리 > API key 리스트")
    public String ApiKeyList(@RequestBody HashMap<String,Object> paramMap){
        Long total;
        List<ApiKeyListAndDetailDto> rows;

        DataTables dataTables = null;

        try{
            HashMap<String, Object> searchMap = null;
            if(paramMap.containsKey("searchData")){
                searchMap = (HashMap<String, Object>) paramMap.get("searchData");
                paramMap.putAll(searchMap);
            }

            rows = apiKeyService.findByApiKeyList(paramMap);
            total = apiKeyService.findByApiKeyListCount(paramMap);

//            dataTables = new DataTables(paramMap, rows, total);

        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dataTables.getJsonString();
    }

}

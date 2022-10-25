package com.app.kokonut.apiKey.service;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.dto.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.dto.ApiKeyListCountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
class ApiKeyServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApiKeyService apiKeyService;

    @Test
    @DisplayName("ApiKey 리스트 호출 테스트")
    public void findByApiKeyList1(){

        // given
        HashMap<String, Object> map = new HashMap<>();
        map.put("useYn","Y");
        map.put("state","");
        map.put("type","");
        map.put("companyIdx","");
        map.put("beInUse","");
        map.put("stimeStart","");
        map.put("searchText","2350ad294");
        map.put("dateType","");

        List<ApiKeyListAndDetailDto> apiKeyListDtos = apiKeyService.findByApiKeyList(map);
        System.out.println("apiKeyListDtos : "+apiKeyListDtos);

        // than
        assertEquals(1L, apiKeyListDtos.size());
    }

    @Test
    @DisplayName("ApiKey 리스트의 Count 조회 테스트")
    public void findByApiKeyListCountTest1(){

        // given
        HashMap<String, Object> map = new HashMap<>();
        map.put("useYn","Y");
        map.put("state","");
        map.put("type","");
        map.put("companyIdx","");
        map.put("beInUse","");
        map.put("stimeStart","");
        map.put("searchText","2350ad294");
        map.put("dateType","");

        ApiKeyListCountDto apiKeyListCountDto = apiKeyService.findByApiKeyListCount(map);
        System.out.println("apiKeyListCountDto : "+apiKeyListCountDto);

        // than
        assertEquals(1L, apiKeyListCountDto.getCount());
    }

    @Test
    @DisplayName("ApiKey 단일 조회(상세보기) : param -> idx 테스트")
    public void findByApiKeyDetailTest1(){

        ApiKeyListAndDetailDto apiKeyDetail = apiKeyService.findByApiKeyDetail(1);
        System.out.println("apiKeyDetail : "+apiKeyDetail);

        // than
        assertEquals(1, apiKeyDetail.getIdx());
    }

    @Test
    @DisplayName("ApiKey 단일 조회 : param -> key 테스트")
    public void findByKeyTest1(){

        // given
        String key = "bbf6e2350ad294913a0c489e692f";

        ApiKeyKeyDto apiKeyKeyDto = apiKeyService.findByKey(key);
        System.out.println("apiKeyKeyDto : "+apiKeyKeyDto);

        // than
        assertEquals(1, apiKeyKeyDto.getIdx());
    }


}
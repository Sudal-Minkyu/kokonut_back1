package com.app.kokonut.apiKey.service;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.dto.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.dto.TestApiKeyExpiredListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@TestPropertySource(locations="classpath:application-test.properties") // 테스트용 db 설정
@AutoConfigureMockMvc
@SpringBootTest
class ApiKeyServiceTest {

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

        Long count = apiKeyService.findByApiKeyListCount(map);
        System.out.println("ApiKey 리스트 count : "+count);

        // than
        assertEquals(1L, count);
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

    @Test
    @DisplayName("TestApiKey 단일 조회 : param -> companyIdx, type = 2 테스트")
    public void findByTestApiKeyByCompanyIdxTest1(){

        ApiKeyListAndDetailDto apiKeyDetail = apiKeyService.findByTestApiKeyByCompanyIdx(1);
        System.out.println("apiKeyDetail : "+apiKeyDetail);

        // than
        assertEquals(1, apiKeyDetail.getIdx());
    }

    @Test
    @DisplayName("TestApiKey 중복 조회 : param -> key, type = 2 테스트")
    public void findByTestApiKeyDuplicateCountTest1(){

        // given
        String key = "bbf6e2350ad294913a0c489e692f";

        Long count = apiKeyService.findByTestApiKeyDuplicateCount(key);
        System.out.println("count : "+count);

        // than
        assertEquals(1L, count);
    }

    @Test
    @DisplayName("ApiKey 단일 조회 : param -> companyIdx, type = 1, useYn = 'Y' 테스트")
    public void findByApiKeyByCompanyIdxTest1(){

        ApiKeyListAndDetailDto apiKeyDetail = apiKeyService.findByApiKeyByCompanyIdx(1);
        System.out.println("apiKeyDetail : "+apiKeyDetail);

        // than
        assertEquals(null, apiKeyDetail);
    }

    @Test
    @DisplayName("ApiKey 중복 조회 : param -> key, type = 1 테스트")
    public void findByApiKeyDuplicateCountTest1(){

        // given
        String key = "bbf6e2350ad294913a0c489e692f";

        Long count = apiKeyService.findByApiKeyDuplicateCount(key);
        System.out.println("count : "+count);

        // than
        assertEquals(0, count);
    }

    @Test
    @DisplayName("만료 예정인 TestApiKey 리스트 조회 테스트")
    public void findByTestApiKeyExpiredListTest1(){

        // given
        HashMap<String, Object> map = new HashMap<>();

        List<TestApiKeyExpiredListDto> apiKeyExpiredListDtos = apiKeyService.findByTestApiKeyExpiredList(map);
        System.out.println("apiKeyExpiredListDtos : "+apiKeyExpiredListDtos);

        // than
//        assertEquals(0, apiKeyExpiredListDtos);
    }

}
package com.app.kokonut.apiKey.service;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.dto.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.dto.TestApiKeyExpiredListDto;
import com.app.kokonut.apiKey.entity.ApiKey;
import com.app.kokonut.apiKey.repository.ApiKeyRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@TestPropertySource(locations="classpath:application-test.properties") // 테스트용 db 설정
@AutoConfigureMockMvc
@SpringBootTest
class ApiKeyServiceTest {

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Test
    @DisplayName("ApiKey 인서트&삭제 테스트 - 성공적으로 인서트(InsertApiKey)하고 해당 데이터를 삭제(DeleteApiKeyByIdx)한 후 다시 조회하는 테스트")
    public void InsertApiKeyAndDeleteApiKeyByIdxTest(){

        // given
        Integer adminIdx = 1;
        Integer companyIdx = 1;
        String registerName = "테스트";
        Integer type = 1;
        Integer state = 1;
        String key = "test_key";

        // when
        Integer createIdx = apiKeyService.InsertApiKey(adminIdx, companyIdx, registerName, type, state, key);
        System.out.println("인서트 완료 createIdx : "+createIdx);

        apiKeyService.DeleteApiKeyByIdx(createIdx);
        System.out.println("삭제 성공 createIdx : "+createIdx);

        Optional<ApiKey> optionalApiKey = apiKeyRepository.findById(createIdx);
        if(optionalApiKey.isEmpty()){
            System.out.println("InsertApiKeyAndDeleteApiKeyByIdxTest : 테스트 성공");
        }
    }

    @Test
    @DisplayName("ApiKey 업데이트&삭제 테스트 - 성공적으로 업데이트(UpdateApiKey)후 삭제(DeleteApiKeyByIdx) 테스트")
    public void UpdateApiKeyAndDeleteApiKeyByIdxTest(){

        // given
        Integer adminIdx = 1;
        Integer companyIdx = 1;
        String registerName = "테스트";
        Integer type = 1;
        Integer state = 1;
        String key = "test_key";

        // when
        Integer createIdx = apiKeyService.InsertApiKey(adminIdx, companyIdx, registerName, type, state, key);
        System.out.println("인서트 완료 createIdx : "+createIdx);

        // when
        apiKeyService.UpdateApiKey(createIdx, "N", "테스트사유", 1, "수정테스트");

        ApiKey apiKey = apiKeyRepository.findById(createIdx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ApiKey' 입니다."));

        // than
        assertEquals(createIdx,apiKey.getIdx());
        assertEquals("테스트",apiKey.getRegisterName());
        assertEquals("테스트사유",apiKey.getReason());

        apiKeyService.DeleteApiKeyByIdx(createIdx);
        System.out.println("삭제 성공 createIdx : "+createIdx);

        Optional<ApiKey> optionalApiKey = apiKeyRepository.findById(createIdx);
        if(optionalApiKey.isEmpty()){
            System.out.println("UpdateApiKeyAndDeleteApiKeyByIdxTest : 테스트 성공");
        }
    }

    @Test
    @DisplayName("ApiKey 리스트 호출 테스트")
    public void findByApiKeyListTest1(){

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

        // when
        List<ApiKeyListAndDetailDto> apiKeyListDtos = apiKeyService.findByApiKeyList(map);
        System.out.println("apiKeyListDtos : "+apiKeyListDtos);

        // than
//        assertEquals(1L, apiKeyListDtos.size());
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

        // when
        Long count = apiKeyService.findByApiKeyListCount(map);
        System.out.println("ApiKey 리스트 count : "+count);

        // than
//        assertEquals(1L, count);
    }

    @Test
    @DisplayName("ApiKey 단일 조회(상세보기) : param -> idx 테스트")
    public void findByApiKeyDetailTest1(){

        // when
        ApiKeyListAndDetailDto apiKeyDetail = apiKeyService.findByApiKeyDetail(1);
        System.out.println("apiKeyDetail : "+apiKeyDetail);

        // than
//        assertEquals(1, apiKeyDetail.getIdx());
    }

    @Test
    @DisplayName("ApiKey 단일 조회 : param -> key 테스트")
    public void findByKeyTest1(){

        // given
        String key = "bbf6e2350ad294913a0c489e692f";

        // when
        ApiKeyKeyDto apiKeyKeyDto = apiKeyService.findByKey(key);
        System.out.println("apiKeyKeyDto : "+apiKeyKeyDto);

        // than
//        assertEquals(1, apiKeyKeyDto.getIdx());
    }

    @Test
    @DisplayName("TestApiKey 단일 조회 : param -> companyIdx, type = 2 테스트")
    public void findByTestApiKeyByCompanyIdxTest1(){

        // when
        ApiKeyListAndDetailDto apiKeyDetail = apiKeyService.findByTestApiKeyByCompanyIdx(1);
        System.out.println("apiKeyDetail : "+apiKeyDetail);

        // than
//        assertEquals(1, apiKeyDetail.getIdx());
    }

    @Test
    @DisplayName("TestApiKey 중복 조회 : param -> key, type = 2 테스트")
    public void findByTestApiKeyDuplicateCountTest1(){

        // given
        String key = "bbf6e2350ad294913a0c489e692f";

        // when
        Long count = apiKeyService.findByTestApiKeyDuplicateCount(key);
        System.out.println("count : "+count);

        // than
//        assertEquals(1L, count);
    }

    @Test
    @DisplayName("ApiKey 단일 조회 : param -> companyIdx, type = 1, useYn = 'Y' 테스트")
    public void findByApiKeyByCompanyIdxTest1(){

        // when
        ApiKeyListAndDetailDto apiKeyDetail = apiKeyService.findByApiKeyByCompanyIdx(1);
        System.out.println("apiKeyDetail : "+apiKeyDetail);

        // than
//        assertNull(apiKeyDetail);
    }

    @Test
    @DisplayName("ApiKey 중복 조회 : param -> key, type = 1 테스트")
    public void findByApiKeyDuplicateCountTest1(){

        // given
        String key = "bbf6e2350ad294913a0c489e692f";

        // when
        Long count = apiKeyService.findByApiKeyDuplicateCount(key);
        System.out.println("count : "+count);

        // than
//        assertEquals(0, count);
    }

    @Test
    @DisplayName("TestApiKey 만료예정 리스트 조회 테스트")
    public void findByTestApiKeyExpiredListTest1(){

        // given
        HashMap<String, Object> map = new HashMap<>();

        // when
        List<TestApiKeyExpiredListDto> apiKeyExpiredListDtos = apiKeyService.findByTestApiKeyExpiredList(map);
        System.out.println("apiKeyExpiredListDtos : "+apiKeyExpiredListDtos);

    }

    @Test
    @DisplayName("ApiKey 결제취소 테스트 - " +
            "1. 성공적으로 인서트(InsertApiKey)하고 결제취소를 호출한다. " +
            "2. 인서트한 값의 결제취소(UpdateBlockKey)를 호출한다. " +
            "3. 해당 데이터를 삭제(DeleteApiKeyByIdx)한 후 다시 조회하여 테스트를 마무리한다.")
    public void UpdateBlockKeyTest(){

        // given
        Integer adminIdx = 1;
        Integer companyIdx = 0;
        String registerName = "테스트";
        Integer type = 1;
        Integer state = 1;
        String key = "test_key";

        // when
        Integer createIdx = apiKeyService.InsertApiKey(adminIdx, companyIdx, registerName, type, state, key);
        System.out.println("인서트 완료 createIdx : "+createIdx);

        Optional<ApiKey> optionalApiKey = apiKeyRepository.findById(createIdx);
        if(optionalApiKey.isPresent()){
            System.out.println("UpdateBlockKeyTest : 인서트 성공");
            assertEquals("Y", optionalApiKey.get().getUseYn());

            apiKeyService.UpdateBlockKey(companyIdx);

            ApiKey apiKey = apiKeyRepository.findApiKeyByCompanyIdxAndType(companyIdx,1)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ApiKey' 입니다."));
            assertEquals("N", apiKey.getUseYn());

            apiKeyService.DeleteApiKeyByIdx(createIdx);
            System.out.println("삭제 성공 createIdx : "+createIdx);

            Optional<ApiKey> optionalDeleteApiKey = apiKeyRepository.findById(createIdx);
            if(optionalDeleteApiKey.isEmpty()){
                System.out.println("UpdateBlockKeyTest : 테스트 성공");
            }
        }
    }

    @Test
    @DisplayName("ApiKey 사용중인 TEST API KEY가 존재한다면 만료처리 테스트 - " +
            "1. 성공적으로 인서트(InsertApiKey)하고 결제취소를 호출한다. " +
            "2. 인서트한 값의 테스트키 만료처리(UpdateTestKeyExpire)를 호출한다. " +
            "3. 해당 데이터를 삭제(DeleteApiKeyByIdx)한 후 다시 조회하여 테스트를 마무리한다.")
    public void UpdateTestKeyExpireTest(){

        // given
        Integer adminIdx = 1;
        Integer companyIdx = 0;
        String registerName = "테스트";
        Integer type = 2;
        Integer state = 1;
        String key = "test_key";

        // when
        Integer createIdx = apiKeyService.InsertApiKey(adminIdx, companyIdx, registerName, type, state, key);
        System.out.println("인서트 완료 createIdx : "+createIdx);

        Optional<ApiKey> optionalApiKey = apiKeyRepository.findById(createIdx);
        if(optionalApiKey.isPresent()){
            System.out.println("UpdateTestKeyExpire : 인서트 성공");
            assertEquals(0, optionalApiKey.get().getCompanyIdx());
            assertEquals(2, optionalApiKey.get().getType());

            apiKeyService.UpdateTestKeyExpire(companyIdx);

            apiKeyService.DeleteApiKeyByIdx(createIdx);
            System.out.println("삭제 성공 createIdx : "+createIdx);

            Optional<ApiKey> optionalDeleteApiKey = apiKeyRepository.findById(createIdx);
            if(optionalDeleteApiKey.isEmpty()){
                System.out.println("UpdateTestKeyExpire : 테스트 성공");
            }
        }
    }

    @Test
    @DisplayName("ApiKey TotalDeleteService DeleteApiKeyByCompanyIdx 테스트 - " +
            "1. 성공적으로 인서트(InsertApiKey)하고 DeleteApiKeyByCompanyIdx를 호출한다. " +
            "2. 인서트한 값의 DeleteApiKeyByCompanyIdx를 호출한다. " +
            "3. 데이터를 삭제(DeleteApiKeyByIdx)한 후 다시 조회하여 테스트를 마무리한다.")
    public void DeleteApiKeyByCompanyIdxTest(){

        // given
        Integer adminIdx = 1;
        Integer companyIdx = 2;
        String registerName = "테스트";
        Integer type = 1;
        Integer state = 1;
        String key = "test_key";

        // when
        Integer createIdx = apiKeyService.InsertApiKey(adminIdx, companyIdx, registerName, type, state, key);
        System.out.println("인서트 완료 createIdx : "+createIdx);

        Optional<ApiKey> optionalApiKey = apiKeyRepository.findById(createIdx);
        if(optionalApiKey.isPresent()){
            System.out.println("DeleteApiKeyByCompanyIdx : 인서트 성공");
            assertEquals(2, optionalApiKey.get().getCompanyIdx());

            apiKeyService.DeleteApiKeyByCompanyIdx(companyIdx);
            System.out.println("삭제 성공 companyIdx : "+companyIdx);

            Optional<ApiKey> optionalDeleteApiKey = apiKeyRepository.findById(createIdx);
            if(optionalDeleteApiKey.isEmpty()){
                System.out.println("DeleteApiKeyByCompanyIdx : 테스트 성공");
            }
        }
    }

}
package com.app.kokonut.apiKey.service;

import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.admin.repository.AdminRepository;
import com.app.kokonut.apiKey.dtos.ApiKeyKeyDto;
import com.app.kokonut.apiKey.dtos.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.dtos.ApiKeyMapperDto;
import com.app.kokonut.apiKey.dtos.TestApiKeyExpiredListDto;
import com.app.kokonut.apiKey.entity.ApiKey;
import com.app.kokonut.apiKey.repository.ApiKeyRepository;
import com.app.kokonut.company.entity.Company;
import com.app.kokonut.company.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
class ApiKeyServiceTest {

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AdminRepository adminRepository;

    // 테스트하기 사전데이터 넣기
    @BeforeEach
    void testDataInsert(){
        Date systemDate = new Date(System.currentTimeMillis());
        System.out.println("현재 날짜 : "+systemDate);

        Company company = new Company();
        company.setCompanyName("기업명");
        company.setRegdate(systemDate);
        Company saveCompany = companyRepository.save(company);
//        System.out.println("저장된 saveCompany : "+saveCompany);

        Admin admin = new Admin();
        admin.setCompanyIdx(saveCompany.getIdx());
        admin.setName("사용자명");
        Admin saveAdmin = adminRepository.save(admin);
//        System.out.println("저장된 saveAdmin : "+saveAdmin);

        List<ApiKey> apiKeyList = new ArrayList<>();

        int adminIdx = saveAdmin.getIdx();
        int companyIdx = saveCompany.getIdx();
        String registerName;
        int type = 1;
        int state = 1;
        String key;
        int useAccumulate= 1;

        // 10번 반복문 돌려 리스트데이터(List<ApiKey>)에 넣는다.
        for(int i=1; i<11; i++){
            ApiKey apiKey = new ApiKey();

            registerName = "테스트_"+i;
            key = "test_key_"+i;
            apiKey.setAdminIdx(adminIdx);
            apiKey.setCompanyIdx(companyIdx);
            apiKey.setRegisterName(registerName);
            apiKey.setType(type);
            apiKey.setState(state);
            apiKey.setUseAccumulate(useAccumulate);
            apiKey.setKey(key);
            apiKey.setRegdate(systemDate);
            apiKey.setUseYn("Y");

            apiKeyList.add(apiKey);
        }

        apiKeyRepository.saveAll(apiKeyList);
    }

    // 사전데이터 삭제
    @AfterEach
    void testDataDelete(){
        adminRepository.deleteAll();
        companyRepository.deleteAll();
        apiKeyRepository.deleteAll();
    }

    @Test
    @DisplayName("ApiKey 인서트&삭제 테스트 - 성공적으로 인서트(insertApiKey)하고 해당 데이터를 삭제(deleteApiKeyByIdx)한 후 다시 조회하는 테스트")
    public void insertApiKeyAndDeleteApiKeyByIdxTest(){

        // given
        Integer adminIdx = 99;
        Integer companyIdx = 99;
        String registerName = "테스트_99";
        Integer type = 1;
        Integer state = 1;
        String key = "test_key_99";
        Integer useAccumulate = 1;

        // when
        Integer createIdx = apiKeyService.insertApiKey(adminIdx, companyIdx, registerName, type, state, key, useAccumulate);
        System.out.println("인서트 완료 createIdx : "+createIdx);

        apiKeyService.deleteApiKeyByIdx(createIdx);
        System.out.println("삭제 성공 createIdx : "+createIdx);

        Optional<ApiKey> optionalApiKey = apiKeyRepository.findById(createIdx);
        if(optionalApiKey.isEmpty()){
            System.out.println("insertApiKeyAndDeleteApiKeyByIdxTest : 테스트 성공");
        }
    }

    @Test
    @DisplayName("ApiKey 업데이트&삭제 테스트 - 성공적으로 업데이트(updateApiKey)후 삭제(deleteApiKeyByIdx) 테스트")
    public void updateApiKeyAndDeleteApiKeyByIdxTest(){

        // given
        Integer adminIdx = 99;
        Integer companyIdx = 99;
        String registerName = "테스트_99";
        Integer type = 1;
        Integer state = 1;
        String key = "test_key_99";
        Integer useAccumulate = 1;

        // when
        Integer createIdx = apiKeyService.insertApiKey(adminIdx, companyIdx, registerName, type, state, key, useAccumulate);
        System.out.println("인서트 완료 createIdx : "+createIdx);

        // when
        apiKeyService.updateApiKey(createIdx, "N", "테스트사유", 1, "수정테스트");

        ApiKey apiKey = apiKeyRepository.findById(createIdx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ApiKey' 입니다."));

        // than
        assertEquals(createIdx,apiKey.getIdx());
        assertEquals("테스트_99",apiKey.getRegisterName());
        assertEquals("테스트사유",apiKey.getReason());

        apiKeyService.deleteApiKeyByIdx(createIdx);
        System.out.println("삭제 성공 createIdx : "+createIdx);

        Optional<ApiKey> optionalApiKey = apiKeyRepository.findById(createIdx);
        if(optionalApiKey.isEmpty()){
            System.out.println("updateApiKeyAndDeleteApiKeyByIdxTest : 테스트 성공");
        }
    }

    @Test
    @DisplayName("ApiKey 리스트 호출 테스트")
    public void findByApiKeyListTest(){

        // given
        ApiKeyMapperDto apiKeyMapperDto = new ApiKeyMapperDto();
        apiKeyMapperDto.setType(1);

        // when
        List<ApiKeyListAndDetailDto> apiKeyListDtos = apiKeyService.findByApiKeyList(apiKeyMapperDto);
//        System.out.println("apiKeyListDtos : "+apiKeyListDtos);

        // than
        assertEquals(10L, apiKeyListDtos.size());
    }

    @Test
    @DisplayName("ApiKey 리스트의 Count 조회 테스트")
    public void findByApiKeyListCountTest(){

        // given
        ApiKeyMapperDto apiKeyMapperDto = new ApiKeyMapperDto();

        // when
        Long count = apiKeyService.findByApiKeyListCount(apiKeyMapperDto);
        System.out.println("ApiKey 리스트 count : "+count);

        // than
        assertEquals(10L, count);
    }

    @Test
    @DisplayName("ApiKey 단일 조회(상세보기) : param -> idx 테스트")
    public void findByApiKeyDetailTest(){

        // when
        ApiKeyListAndDetailDto apiKeyDetail = apiKeyService.findByApiKeyDetail(1);
        System.out.println("apiKeyDetail : "+apiKeyDetail);

        // than
//        assertEquals(1, apiKeyDetail.getIdx());
    }

    @Test
    @DisplayName("ApiKey 단일 조회 : param -> key 테스트")
    public void findByKeyTest(){
        // given
        String key = "test_key_1";

        // when
        ApiKeyKeyDto apiKeyKeyDto = apiKeyService.findByKey(key);
//        System.out.println("apiKeyKeyDto : "+apiKeyKeyDto);

        // than
        assertEquals("test_key_1", apiKeyKeyDto.getKey());
        assertEquals(1, apiKeyKeyDto.getType());
    }

    @Test
    @DisplayName("TestApiKey 단일 조회 : param -> companyIdx, type = 2 테스트")
    public void findByTestApiKeyByCompanyIdxTest(){

        // when
        ApiKeyListAndDetailDto apiKeyDetail = apiKeyService.findByTestApiKeyByCompanyIdx(1);
        System.out.println("apiKeyDetail : "+apiKeyDetail);

        // than
//        assertEquals(1, apiKeyDetail.getIdx());
    }

    @Test
    @DisplayName("TestApiKey 중복 조회 : param -> key, type = 2 테스트")
    public void findByTestApiKeyDuplicateCountTest(){

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
    public void findByApiKeyByCompanyIdxTest(){

        // when
        ApiKeyListAndDetailDto apiKeyDetail = apiKeyService.findByApiKeyByCompanyIdx(1);
        System.out.println("apiKeyDetail : "+apiKeyDetail);

        // than
//        assertNull(apiKeyDetail);
    }

    @Test
    @DisplayName("ApiKey 중복 조회 : param -> key, type = 1 테스트")
    public void findByApiKeyDuplicateCountTest(){

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
    public void findByTestApiKeyExpiredListTest(){

        // given
        HashMap<String, Object> map = new HashMap<>();

        // when
        List<TestApiKeyExpiredListDto> apiKeyExpiredListDtos = apiKeyService.findByTestApiKeyExpiredList(map);
        System.out.println("apiKeyExpiredListDtos : "+apiKeyExpiredListDtos);

    }

    @Test
    @DisplayName("ApiKey 결제취소 테스트 - " +
            "1. 성공적으로 인서트(insertApiKey)하고 결제취소를 호출한다. " +
            "2. 인서트한 값의 결제취소(updateBlockKey)를 호출한다. " +
            "3. 해당 데이터를 삭제(deleteApiKeyByIdx)한 후 다시 조회하여 테스트를 마무리한다.")
    public void updateBlockKeyTest(){

        // given
        Integer adminIdx = 99;
        Integer companyIdx = 99;
        String registerName = "테스트_99";
        Integer type = 1;
        Integer state = 1;
        String key = "test_key_99";
        Integer useAccumulate = 1;

        // when
        Integer createIdx = apiKeyService.insertApiKey(adminIdx, companyIdx, registerName, type, state, key, useAccumulate);
        System.out.println("인서트 완료 createIdx : "+createIdx);

        Optional<ApiKey> optionalApiKey = apiKeyRepository.findById(createIdx);
        if(optionalApiKey.isPresent()){
            System.out.println("updateBlockKeyTest : 인서트 성공");
            assertEquals("Y", optionalApiKey.get().getUseYn());

            apiKeyService.updateBlockKey(companyIdx);

            ApiKey apiKey = apiKeyRepository.findApiKeyByCompanyIdxAndType(companyIdx,1)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'ApiKey' 입니다."));
            assertEquals("N", apiKey.getUseYn());

            apiKeyService.deleteApiKeyByIdx(createIdx);
            System.out.println("삭제 성공 createIdx : "+createIdx);

            Optional<ApiKey> optionalDeleteApiKey = apiKeyRepository.findById(createIdx);
            if(optionalDeleteApiKey.isEmpty()){
                System.out.println("updateBlockKeyTest : 테스트 성공");
            }
        }
    }

    @Test
    @DisplayName("ApiKey 사용중인 TEST API KEY가 존재한다면 만료처리 테스트 - " +
            "1. 성공적으로 인서트(insertApiKey)하고 결제취소를 호출한다. " +
            "2. 인서트한 값의 테스트키 만료처리(updateTestKeyExpire)를 호출한다. " +
            "3. 해당 데이터를 삭제(deleteApiKeyByIdx)한 후 다시 조회하여 테스트를 마무리한다.")
    public void updateTestKeyExpireTest(){

        // given
        Integer adminIdx = 99;
        Integer companyIdx = 99;
        String registerName = "테스트_99";
        Integer type = 2;
        Integer state = 1;
        String key = "test_key_99";
        Integer useAccumulate = 1;

        // when
        Integer createIdx = apiKeyService.insertApiKey(adminIdx, companyIdx, registerName, type, state, key, useAccumulate);
        System.out.println("인서트 완료 createIdx : "+createIdx);

        Optional<ApiKey> optionalApiKey = apiKeyRepository.findById(createIdx);
        if(optionalApiKey.isPresent()){
            System.out.println("updateTestKeyExpire : 인서트 성공");
            assertEquals(99, optionalApiKey.get().getCompanyIdx());
            assertEquals(2, optionalApiKey.get().getType());

            apiKeyService.updateTestKeyExpire(companyIdx);

            apiKeyService.deleteApiKeyByIdx(createIdx);
            System.out.println("삭제 성공 createIdx : "+createIdx);

            Optional<ApiKey> optionalDeleteApiKey = apiKeyRepository.findById(createIdx);
            if(optionalDeleteApiKey.isEmpty()){
                System.out.println("UpdateTestKeyExpire : 테스트 성공");
            }
        }
    }

    @Test
    @DisplayName("ApiKey TotalDeleteService deleteApiKeyByCompanyIdx 테스트 - " +
            "1. 성공적으로 인서트(insertApiKey)하고 deleteApiKeyByCompanyIdx를 호출한다. " +
            "2. 인서트한 값의 deleteApiKeyByCompanyIdx를 호출한다. " +
            "3. 데이터를 삭제(deleteApiKeyByIdx)한 후 다시 조회하여 테스트를 마무리한다.")
    public void deleteApiKeyByCompanyIdxTest(){

        // given
        Integer adminIdx = 99;
        Integer companyIdx = 99;
        String registerName = "테스트_99";
        Integer type = 1;
        Integer state = 1;
        String key = "test_key_99";
        Integer useAccumulate = 1;

        // when
        Integer createIdx = apiKeyService.insertApiKey(adminIdx, companyIdx, registerName, type, state, key, useAccumulate);
        System.out.println("인서트 완료 createIdx : "+createIdx);

        Optional<ApiKey> optionalApiKey = apiKeyRepository.findById(createIdx);
        if(optionalApiKey.isPresent()){
            System.out.println("deleteApiKeyByCompanyIdx : 인서트 성공");
            assertEquals(99, optionalApiKey.get().getCompanyIdx());

            apiKeyService.deleteApiKeyByCompanyIdx(companyIdx);
            System.out.println("삭제 성공 companyIdx : "+companyIdx);

            Optional<ApiKey> optionalDeleteApiKey = apiKeyRepository.findById(createIdx);
            if(optionalDeleteApiKey.isEmpty()){
                System.out.println("deleteApiKeyByCompanyIdx : 테스트 성공");
            }
        }
    }

}
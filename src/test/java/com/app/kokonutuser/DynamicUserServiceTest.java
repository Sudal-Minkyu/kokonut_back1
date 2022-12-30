package com.app.kokonutuser;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.admin.entity.enums.AuthorityRole;
import com.app.kokonut.company.Company;
import com.app.kokonut.company.CompanyRepository;
import com.app.kokonut.email.email.EmailRepository;
import com.app.kokonut.email.email.EmailService;
import com.app.kokonut.email.email.entity.Email;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class DynamicUserServiceTest {

    private final String email = "kokonut@kokonut.me";

    private Integer saveAdminIdx;

    private Integer saveCompanyIdx;

    @Autowired
    private DynamicUserService dynamicUserService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CompanyRepository companyRepository;


    @BeforeEach
    void testDataInsert() {

        // 테스트용 company
        Company company = Company.builder()
                .businessNumber("00001111")
                .regdate(LocalDateTime.now())
                .build();

        saveCompanyIdx= companyRepository.save(company).getIdx();

        // 테스트용 admin
        Admin admin = Admin.builder()
                .email(email)
                .password("test")
                .companyIdx(saveCompanyIdx)
                .roleName(AuthorityRole.ROLE_MASTER)
                .regdate(LocalDateTime.now())
                .build();

        saveAdminIdx = adminRepository.save(admin).getIdx();

    }

    @AfterEach
    void testDataDelete() {
        adminRepository.deleteAll();
        companyRepository.deleteAll();
    }

//    @Test
//    @DisplayName("회원 테이블 생성 성공 테스트")
//    void createTableKokonutUserTest1() {
//
//        System.out.println("저장된 saveAdminIdx : "+saveAdminIdx);
//
//        System.out.println("저장된 saveCompanyIdx : "+saveCompanyIdx);
//
//        ResponseEntity<Map<String, Object>> response = dynamicUserService.createTable(email);
//        System.out.println("response : "+response);
//    }
}
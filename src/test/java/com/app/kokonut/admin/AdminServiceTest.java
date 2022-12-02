package com.app.kokonut.admin;

import com.app.kokonut.apiKey.ApiKeyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Woody
 * Date : 2022-12-02
 * Time :
 * Remark : AdminService 테스트코드
 */
@AutoConfigureMockMvc
@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;





    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void signUp() {
    }

    @Test
    void authToken() {
    }

    @Test
    void reissue() {
    }

    @Test
    void logout() {
    }

    @Test
    void authorityCheck() {
    }
}
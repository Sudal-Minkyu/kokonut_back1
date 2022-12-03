package com.app.kokonut.admin;

import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.admin.entity.enums.AuthorityRole;
import com.app.kokonut.admin.jwt.been.JwtTokenProvider;
import com.app.kokonut.admin.jwt.dto.AuthRequestDto;
import com.app.kokonut.admin.jwt.dto.AuthResponseDto;
import com.app.kokonut.woody.common.ResponseErrorCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    private final String testemail = "testkokonut@kokonut.me";

    private final String email = "kokonut@kokonut.me";

    private final String password = "kokonut123!!";

    private String testAccessToken = "";
    private String testRefreshAccessToken = "";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void testDataInsert() {
        AuthRequestDto.SignUp signUp = new AuthRequestDto.SignUp();
        signUp.setEmail(testemail);
        signUp.setPassword(password);

        adminService.signUp(signUp);

        AuthRequestDto.Login login = new AuthRequestDto.Login();
        login.setEmail(signUp.getEmail());
        login.setPassword(signUp.getPassword());

        ResponseEntity<Map<String, Object>> response = adminService.authToken(login);

        HashMap<String,Object> sendData = (HashMap<String, Object>) Objects.requireNonNull(response.getBody()).get("sendData");
        AuthResponseDto.TokenInfo tokenInfo = (AuthResponseDto.TokenInfo) sendData.get("jwtToken");
        testAccessToken = tokenInfo.getAccessToken();
        testRefreshAccessToken = tokenInfo.getRefreshToken();
    }

    @AfterEach
    void testDataDelete() {
        adminRepository.deleteAll();
    }

    @Test
    @DisplayName("사업자 회원가입 성공 테스트 - 회원가입이후 조회 및 검증")
    public void signUpTest1(){

        // given
        AuthRequestDto.SignUp signUp = new AuthRequestDto.SignUp();
        signUp.setEmail(email);
        signUp.setPassword(password);

        // when
        ResponseEntity<Map<String,Object>> response =  adminService.signUp(signUp);

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'Admin' 입니다."));

        // then
        assertEquals(email,admin.getEmail());
        assertEquals(AuthorityRole.ROLE_MASTER,admin.getRoleName());

        assertEquals("SUCCESS", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(200, Objects.requireNonNull(response.getBody()).get("status"));

    }

    @Test
    @DisplayName("사업자 회원가입 실패 테스트 - 이메일이 이미 존재 할 경우")
    public void signUpTest2(){

        // given
        AuthRequestDto.SignUp signUp = new AuthRequestDto.SignUp();
        signUp.setEmail(testemail);
        signUp.setPassword(password);

        // when
        ResponseEntity<Map<String,Object>> response = adminService.signUp(signUp);

        // then
        assertEquals(ResponseErrorCode.KO005.getCode(), Objects.requireNonNull(response.getBody()).get("err_code"));
        assertEquals("Error", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(500, Objects.requireNonNull(response.getBody()).get("status"));

    }

    @Test
    @DisplayName("로그인후 JWT토큰 발급 성공 테스트 - 발급받은 accessToken 확인")
    @SuppressWarnings("unchecked")
    public void authTokenTest1(){

        // given
        AuthRequestDto.Login login = new AuthRequestDto.Login();
        login.setEmail(testemail);
        login.setPassword(password);

        // when
        ResponseEntity<Map<String,Object>> response =  adminService.authToken(login);

        HashMap<String,Object> sendData = (HashMap<String, Object>) Objects.requireNonNull(response.getBody()).get("sendData");
        AuthResponseDto.TokenInfo tokenInfo = (AuthResponseDto.TokenInfo) sendData.get("jwtToken");
        boolean result = jwtTokenProvider.validateToken(tokenInfo.getAccessToken());

        // then
        assertTrue(result);
        assertEquals("SUCCESS", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(200, Objects.requireNonNull(response.getBody()).get("status"));
    }

    @Test
    @DisplayName("로그인후 JWT토큰 발급 실패 테스트 - 존재하지 않은 이메일일 경우")
    public void authTokenTest2(){

        // given
        AuthRequestDto.Login login = new AuthRequestDto.Login();
        login.setEmail(email);
        login.setPassword(password);

        // when
        ResponseEntity<Map<String,Object>> response =  adminService.authToken(login);

        // then
        assertEquals(ResponseErrorCode.KO004.getCode(), Objects.requireNonNull(response.getBody()).get("err_code"));
        assertEquals("Error", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(500, Objects.requireNonNull(response.getBody()).get("status"));

    }

    @Test
    @DisplayName("JWT토큰 새로고침 성공 테스트")
    @SuppressWarnings("unchecked")
    public void reissueTest1(){

        // given
        AuthRequestDto.Reissue reissue = new AuthRequestDto.Reissue();
        reissue.setAccessToken(testAccessToken);
        reissue.setRefreshToken(testRefreshAccessToken);

        boolean resultTrue = jwtTokenProvider.validateToken(testAccessToken);
        assertTrue(resultTrue); // 해당 토큰이 유효한지 테스트

        // when
        ResponseEntity<Map<String,Object>> response =  adminService.reissue(reissue);

        boolean resultFalse = jwtTokenProvider.validateToken(testAccessToken);
        assertTrue(resultFalse); // 해당 토큰이 유효하지 않은지 테스트

        HashMap<String,Object> sendData = (HashMap<String, Object>) Objects.requireNonNull(response.getBody()).get("sendData");
        AuthResponseDto.TokenInfo tokenInfo = (AuthResponseDto.TokenInfo) sendData.get("jwtToken");
        boolean result = jwtTokenProvider.validateToken(tokenInfo.getAccessToken());

        // then
        assertTrue(result); // 새로받은 토큰이 유효한지 테스트
        assertEquals("SUCCESS", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(200, Objects.requireNonNull(response.getBody()).get("status"));
    }

    @Test
    @DisplayName("로그인 JWT토큰 새로고침 실패 테스트 - 유효한 Refresh 토큰이 아닐 경우")
    public void reissueTest2(){

        // given
        AuthRequestDto.Reissue reissue = new AuthRequestDto.Reissue();
        reissue.setAccessToken(testAccessToken);
        reissue.setRefreshToken(testRefreshAccessToken+"test");

        // when
        ResponseEntity<Map<String,Object>> response =  adminService.reissue(reissue);

        // then
        assertEquals(ResponseErrorCode.KO007.getCode(), Objects.requireNonNull(response.getBody()).get("err_code"));
        assertEquals("Error", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(500, Objects.requireNonNull(response.getBody()).get("status"));
    }

    @Test
    @DisplayName("JWT토큰 새로고침 실패 테스트 - 받은 토큰이 이미 로그아웃된 토큰일 경우")
    public void reissueTest3(){

        // given
        AuthRequestDto.Reissue reissue = new AuthRequestDto.Reissue();
        reissue.setAccessToken(testAccessToken);
        reissue.setRefreshToken(testRefreshAccessToken);

        boolean resultTrue = jwtTokenProvider.validateToken(testAccessToken);
        assertTrue(resultTrue); // 해당 토큰이 유효한지 테스트

        AuthRequestDto.Logout logout = new AuthRequestDto.Logout();
        logout.setAccessToken(testAccessToken);
        logout.setRefreshToken(testRefreshAccessToken);
        adminService.logout(logout);

        // when
        ResponseEntity<Map<String,Object>> response =  adminService.reissue(reissue);

        // then
        assertEquals(ResponseErrorCode.KO006.getCode(), Objects.requireNonNull(response.getBody()).get("err_code"));
        assertEquals("Error", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(500, Objects.requireNonNull(response.getBody()).get("status"));
    }

    @Test
    @DisplayName("JWT토큰 새로고침 실패 테스트 - Access 토큰의 Refresh 토큰이 맞지 않을 경우")
    public void reissueTest4(){

        // given
        AuthRequestDto.Reissue reissue = new AuthRequestDto.Reissue();
        reissue.setAccessToken(testAccessToken);
        reissue.setRefreshToken(testRefreshAccessToken);

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("RT: "+testemail, testRefreshAccessToken+"test");

        // when
        ResponseEntity<Map<String,Object>> response =  adminService.reissue(reissue);

        // then
        assertEquals(ResponseErrorCode.KO008.getCode(), Objects.requireNonNull(response.getBody()).get("err_code"));
        assertEquals("Error", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(500, Objects.requireNonNull(response.getBody()).get("status"));
    }

    @Test
    @DisplayName("로그아웃 성공 테스트")
    public void logoutTest1(){

        // given
        AuthRequestDto.Logout logout = new AuthRequestDto.Logout();
        logout.setAccessToken(testAccessToken);
        logout.setRefreshToken(testRefreshAccessToken);

        boolean resultTrue = jwtTokenProvider.validateToken(testAccessToken);
        assertTrue(resultTrue); // 해당 토큰이 유효한지 테스트

        // when
        ResponseEntity<Map<String,Object>> response =  adminService.logout(logout);

        boolean resultFalse = jwtTokenProvider.validateToken(testAccessToken);
        assertTrue(resultFalse); // 해당 토큰이 유효하지 않은지 테스트

        String result = redisTemplate.opsForValue().get(logout.getAccessToken()); // 해당 토큰이 BlackList로 올라갔는지 테스트

        // then
        assertEquals("logout", result);
        assertEquals("SUCCESS", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(200, Objects.requireNonNull(response.getBody()).get("status"));
    }

    @Test
    @DisplayName("로그아웃 실패 테스트 - 받은 Access 토큰정보가 유효하지 않을 경우")
    public void logoutTest2(){

        // given
        AuthRequestDto.Logout logout = new AuthRequestDto.Logout();
        logout.setAccessToken(testAccessToken+"test");
        logout.setRefreshToken(testRefreshAccessToken);

        // when
        ResponseEntity<Map<String,Object>> response =  adminService.logout(logout);

        // then
        assertEquals(ResponseErrorCode.KO006.getCode(), Objects.requireNonNull(response.getBody()).get("err_code"));
        assertEquals("Error", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(500, Objects.requireNonNull(response.getBody()).get("status"));
    }


}
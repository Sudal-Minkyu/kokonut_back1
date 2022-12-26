package com.app.kokonut.email.email;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.admin.entity.enums.AuthorityRole;
import com.app.kokonut.email.email.dto.EmailDetailDto;
import com.app.kokonut.email.email.entity.Email;
import com.app.kokonut.woody.common.ResponseErrorCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
class EmailServiceTest {

    private final String email = "kokonut@kokonut.me";

    private Integer saveAdminIdx;

    private Integer saveEmailIdx;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private AdminRepository adminRepository;

    @BeforeEach
    void testDataInsert() {

        // 테스트용 admin
        Admin admin = Admin.builder()
                .email(email)
                .password("test")
                .roleName(AuthorityRole.ROLE_MASTER)
                .regdate(LocalDateTime.now())
                .build();

        saveAdminIdx = adminRepository.save(admin).getIdx();

        Email saveEmail = new Email();
        saveEmail.setTitle("테스트제목");
        saveEmail.setContents("테스트내용");
        saveEmail.setSenderAdminIdx(saveAdminIdx);
        saveEmail.setReceiverType("I");
        saveEmail.setReceiverAdminIdxList("1");
        saveEmail.setRegdate(LocalDateTime.now());

        saveEmailIdx = emailRepository.save(saveEmail).getIdx();

    }

    @AfterEach
    void testDataDelete() {
        adminRepository.deleteAll();
        emailRepository.deleteAll();
    }

    @Test
    @DisplayName("이메일 리스트 호출 성공 테스트")
    public void emailListTest1() {

        List<Email> emailList = new ArrayList<>();
        for(int i=1; i<11; i++) {
            Email saveEmail = new Email();
            saveEmail.setTitle("테스트제목"+i);
            saveEmail.setContents("테스트내용"+i);
            saveEmail.setSenderAdminIdx(saveAdminIdx);
            saveEmail.setReceiverType("I");
            saveEmail.setReceiverAdminIdxList("1");
            saveEmail.setRegdate(LocalDateTime.now());
            emailList.add(saveEmail);
        }
        emailRepository.saveAll(emailList);

        // given
        PageRequest pageable = PageRequest.of(0, 10);

        // when
        ResponseEntity<Map<String,Object>> response =  emailService.emailList(pageable);

        // then
        assertEquals("SUCCESS", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(200, Objects.requireNonNull(response.getBody()).get("status"));

    }

    @Test
    @DisplayName("이메일 보내기 성공 테스트")
    public void sendEmailTest1() {

        // given
        EmailDetailDto emailDetailDto = new EmailDetailDto();
        emailDetailDto.setSenderAdminIdx(saveAdminIdx);
        emailDetailDto.setReceiverType("I");
        emailDetailDto.setReceiverAdminIdxList("1");
        emailDetailDto.setTitle("제목 후");
        emailDetailDto.setContents("내용 후...");

        // when
        ResponseEntity<Map<String,Object>> response =  emailService.sendEmail(email, emailDetailDto);

        // then
        assertEquals("SUCCESS", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(200, Objects.requireNonNull(response.getBody()).get("status"));

    }

    @Test
    @DisplayName("이메일 보내기 실패 테스트 - 받는사람 타입을 알 수 없을 경우")
    public void sendEmailTest2() {

        // given
        EmailDetailDto emailDetailDto = new EmailDetailDto();
        emailDetailDto.setSenderAdminIdx(saveAdminIdx);
        emailDetailDto.setReceiverType("A");
        emailDetailDto.setReceiverAdminIdxList("1");
        emailDetailDto.setTitle("제목 후");
        emailDetailDto.setContents("내용 후...");

        // when
        ResponseEntity<Map<String,Object>> response =  emailService.sendEmail(email, emailDetailDto);

        // then
        assertEquals(ResponseErrorCode.KO040.getCode(), Objects.requireNonNull(response.getBody()).get("err_code"));
        assertEquals("Error", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(500, Objects.requireNonNull(response.getBody()).get("status"));

    }


    @Test
    @DisplayName("이메일 상세보기 성공 테스트")
    @SuppressWarnings("unchecked")
    public void sendEmailDetailTest1() {

        // when
        ResponseEntity<Map<String,Object>> response =  emailService.sendEmailDetail(saveEmailIdx);

        HashMap<String,Object> sendData = (HashMap<String, Object>) Objects.requireNonNull(response.getBody()).get("sendData");
        String emailList =  String.valueOf(sendData.get("emailList"));
        String title = String.valueOf(sendData.get("title"));
        String contents = String.valueOf(sendData.get("contents"));

        // then
        assertEquals(email, emailList);
        assertEquals("테스트제목", title);
        assertEquals("테스트내용", contents);
        assertEquals("SUCCESS", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(200, Objects.requireNonNull(response.getBody()).get("status"));

    }

    @Test
    @DisplayName("이메일 상세보기 실패 테스트 - 요청한 idx가 Null값 일 경우")
    public void sendEmailDetailTest2() {

        // when
        ResponseEntity<Map<String,Object>> response =  emailService.sendEmailDetail(null);

        // then
        assertEquals(ResponseErrorCode.KO031.getCode(), Objects.requireNonNull(response.getBody()).get("err_code"));
        assertEquals("Error", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(500, Objects.requireNonNull(response.getBody()).get("status"));

    }

    @Test
    @DisplayName("이메일 상세보기 실패 테스트 - 요청한 idx가 존재하지 않을 경우")
    public void sendEmailDetailTest3() {

        // when
        ResponseEntity<Map<String,Object>> response =  emailService.sendEmailDetail(2);

        // then
        assertEquals(ResponseErrorCode.KO031.getCode(), Objects.requireNonNull(response.getBody()).get("err_code"));
        assertEquals("Error", Objects.requireNonNull(response.getBody()).get("message"));
        assertEquals(500, Objects.requireNonNull(response.getBody()).get("status"));

    }



}
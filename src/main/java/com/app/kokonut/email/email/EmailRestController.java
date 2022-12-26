package com.app.kokonut.email.email;

import com.app.kokonut.email.email.dto.EmailDetailDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/Email")
public class EmailRestController {
    // 기존 코코넛 SystemEmailController 컨트롤러 리팩토링
    // 기존 url : /system/email , 변경 url : /api/Email
    private final EmailService emailService;

    @Autowired
    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }
    @ApiOperation(value="이메일 목록 조회", notes="발송 메일 목록 조회")
    @GetMapping(value = "/emailList") // -> 기존의 코코넛 호출 메서드명 : getEmail
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> emailList(Pageable pageable) {
         return emailService.emailList(pageable);
    }

    @ApiOperation(value="이메일 보내기", notes="이메일 전송")
    @PostMapping("/sendEmail")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> sendEmail(@RequestBody EmailDetailDto emailDetailDto) {
        return emailService.sendEmail(emailDetailDto);
    }

    @ApiOperation(value="이메일 상세보기", notes="메일 상세 내용 조회")
    @GetMapping("/sendEmail/detail/{idx}")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> sendEmailDetail(@PathVariable("idx") Integer idx) {
        return emailService.sendEmailDetail(idx);
    }
    // 기존 코코넛 컨트롤러
    // 이동 mappingValue : /emailManagement, view : /System/Email/EmailManagementUI
    // 로직 mappingValue : /getEmail
    // 이동 mappingValue : /sendEmail, view : /System/Email/SendEmailUI
    // 이동 mappingValue : /sendEmail
    // 이동, 로직 mappingValue : /sendEmail/detail/{idx}, view : /System/Email/SendEmailUI
    // 이동, 로직 mappingValue : /selectEmailTargetPopup, view : /System/Email/Popup/SelectEmailTargetPopup
    // 이동 mappingValue : /adminMemberEmailManagement, view : /System/Email/EmailManagementUI
    // 이동 mappingValue : /adminMemberPrivacyNotice, view : /System/Email/AdminPrivacyNoticeUI
}

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

    // TODO 메일 관리 페이지 이동 컨트롤러 /emailManagement, /System/Email/EmailManagementUI
    // TODO 메일 보내기 페이지 이동 컨트롤러 /sendEmail, /System/Email/SendEmailUI

    @ApiOperation(value="이메일 목록 조회", notes="발송 메일 목록 조회")
    @GetMapping(value = "/getEmail")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> getEmail(@RequestBody Pageable pageable) {
         return emailService.getEmail(pageable);
    }

    @ApiOperation(value="이메일 보내기", notes="이메일 전송")
    @PostMapping("/sendEmail") // 메일 보내기
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> sendEmail(EmailDetailDto emailDetailDto) {
        return emailService.sendEmail(emailDetailDto);
    }

    @ApiOperation(value="이메일 상세보기", notes="메일 상세 내용 조회")
    @GetMapping("/sendEmail/detail/{idx}") // 메일 상세보기
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> sendEmailDetail(@PathVariable("idx") Integer idx) {
        return emailService.sendEmailDetail(idx);
    }



}

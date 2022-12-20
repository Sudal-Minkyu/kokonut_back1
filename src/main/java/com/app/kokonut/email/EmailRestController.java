package com.app.kokonut.email;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/Email")
public class EmailRestController {
    private final EmailService emailService;

    @Autowired
    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }
    @ApiOperation(value="메일 목록 조회", notes="발송 메일 목록 조회")
    @PostMapping(value = "/getEmail")
    public ResponseEntity<Map<String,Object>> getEmail(@RequestBody Pageable pageable) throws Exception {
         return emailService.getEmail(pageable);
    }
    @ApiOperation(value="메일 상세보기", notes="메일 상세 내용 조회")
    @GetMapping("/sendEmail/detail/{idx}") // 메일 상세보기
    public ResponseEntity<Map<String,Object>> sendEmailDetail(@PathVariable("idx") Integer idx) throws Exception {
        return emailService.sendEmailDetail(idx);
    }

}

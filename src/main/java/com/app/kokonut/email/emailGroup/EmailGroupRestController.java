package com.app.kokonut.email.emailGroup;

import com.app.kokonut.email.emailGroup.dto.EmailGroupDetailDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "")
@Validated
@RestController
@RequestMapping("/api/EmailGroup")
public class EmailGroupRestController {
    private final EmailGroupService emailGroupService;

    public EmailGroupRestController(EmailGroupService emailGroupService) {
        this.emailGroupService = emailGroupService;
    }
    @ApiOperation(value="이메일 그룹 목록 조회", notes="이메일 그룹 목록 조")
    @GetMapping(value = "/getEmailGroup")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> getEmailGroup(@RequestBody Pageable pageable) {
        return emailGroupService.findEmailGroupPage(pageable);
    }

    @ApiOperation(value="이메일 그룹 저장", notes="이메일 그룹 저장")
    @PostMapping("/putEmailGroup") // 메일 보내기
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> putEmailGroup(@RequestBody EmailGroupDetailDto emailGroupDetailDto) {
        return emailGroupService.saveEmailGroup(emailGroupDetailDto);
    }

    @ApiOperation(value="이메일 그룹 삭제", notes="이메일 그룹 사용상태 변경")
    @PostMapping("/deleteEmailGroup") // 메일 상세보기
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> deleteEmailGroup(@RequestParam(name="idx") Integer idx){
        return emailGroupService.deleteEmailGroup(idx);
    }

    @ApiOperation(value="이메일 그룹 수정", notes="이메일 그룹 수정")
    @PostMapping("/updateEmailGroup") // 메일 상세보기
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> updateEmailGroup(@RequestBody EmailGroupDetailDto emailGroupDetailDto){
        return emailGroupService.UpdateEmailGroup(emailGroupDetailDto);
    }
}

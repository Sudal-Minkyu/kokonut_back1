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
@RequestMapping("/v2/api/EmailGroup")
public class EmailGroupRestController {
    // 기존 코코넛 SystemEmailGroupController 컨트롤러 리팩토링
    // 기존 url : /system/emailGroup , 변경 url : /api/EmailGroup
    private final EmailGroupService emailGroupService;

    public EmailGroupRestController(EmailGroupService emailGroupService) {
        this.emailGroupService = emailGroupService;
    }
    @ApiOperation(value="이메일 그룹 목록 조회", notes="이메일 그룹 목록 조회")
    @GetMapping(value = "/emailGroupList") // -> 기존의 코코넛 호출 메서드명 : getEmailGroup
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> emailGroupList(@RequestBody Pageable pageable) {
        return emailGroupService.emailGroupList(pageable);
    }

    @ApiOperation(value="이메일 그룹 상세조회", notes="이메일 그룹 상세조회")
    @GetMapping(value = "/emailGroupDetail/{idx}") // -> 기존의 코코넛 호출 메서드명 : SelectEmailGroupByIdx
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> emailGroupDetail(@PathVariable("idx") Integer idx) {
        return emailGroupService.emailGroupDetail(idx);
    }

    @ApiOperation(value="이메일 그룹 저장", notes="이메일 그룹 저장")
    @PostMapping("/saveEmailGroup") // -> 기존의 코코넛 호출 메서드명 : save
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> saveEmailGroup(@RequestBody EmailGroupDetailDto emailGroupDetailDto) {
        return emailGroupService.saveEmailGroup(emailGroupDetailDto);
    }

    @ApiOperation(value="이메일 그룹 삭제", notes="이메일 그룹 사용상태 변경")
    @PostMapping("/deleteEmailGroup") // -> 기존의 코코넛 호출 메서드명 : delete
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> deleteEmailGroup(@RequestParam(name="idx") Integer idx){
        return emailGroupService.deleteEmailGroup(idx);
    }

    @ApiOperation(value="이메일 그룹 수정", notes="이메일 그룹 수정")
    @PostMapping("/updateEmailGroup") // -> 기존의 코코넛 호출 메서드명 : update
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> updateEmailGroup(@RequestBody EmailGroupDetailDto emailGroupDetailDto){
        return emailGroupService.UpdateEmailGroup(emailGroupDetailDto);
    }


    // 기존 코코넛 컨트롤러
    // 이동 mappingValue : /groupManagement, view : /System/EmailGroup/GroupManagementUI
    // 로직 mappingValue : /getEmailGroup
    // 로직 mappingValue : /save
    // 로직 mappingValue : /delete/{idx}
    // 로직 mappingValue : /update
    // 이동 mappingValue : /addGroupPopup, view : /System/EmailGroup/Popup/AddGroupPopup
    // 이동, 로직 mappingValue : /modifyGroupPopup/{idx}, view : /System/EmailGroup/Popup/AddGroupPopup"

    // 이동 mappingValue : /sendEmail
    // 이동, 로직 mappingValue : /sendEmail/detail/{idx}, view : /System/Email/SendEmailUI
    // 이동, 로직 mappingValue : /selectEmailTargetPopup, view : /System/Email/Popup/SelectEmailTargetPopup
    // 이동 mappingValue : /adminMemberEmailManagement, view : /System/Email/EmailManagementUI
    // 이동 mappingValue : /adminMemberPrivacyNotice, view : /System/Email/AdminPrivacyNoticeUI
}

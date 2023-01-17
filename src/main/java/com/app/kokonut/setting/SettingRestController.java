package com.app.kokonut.setting;


import com.app.kokonut.auth.jwt.SecurityUtil;
import com.app.kokonut.setting.dto.SettingDetailDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Joy
 * Date : 2023-01-05
 * Time :
 * Remark : SettingRestController 관리자 환경설정 컨트롤러
 */
@Api(tags = "")
@Validated
@RestController
@RequestMapping("api/Setting")
public class SettingRestController {

    /* 기존 컨트롤러
     * SystemSettingController  "/system/setting"
     * MemberSettingController  "/member/setting"
     * SystemAdminSettingController "/system/adminSetting"
     *
     * 변경 컨트롤러
     * SettingRestController    "/api/Setting"
     */

    private final SettingService settingService;

    public SettingRestController(SettingService settingService) {
        this.settingService = settingService;
    }

    @ApiOperation(value="관리자 환경설정 조회", notes="관리자 환경설정 상세 조회")
    @PostMapping("/settingDetail") // -> 기존의 코코넛 호출 메서드명 : settingsUI, settingsUI - SystemSettingController, MemberSettingController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> settingDetail(){
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        String email = SecurityUtil.getCurrentJwt().getEmail();
        return settingService.settingDetail(userRole, email);
    }

    @ApiOperation(value="관리자 환경설정 저장", notes="관리자 환경설정 저장")
    @PostMapping("/settingSave") // -> 기존의 코코넛 호출 메서드명 : /save - MemberSettingController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> settingSave(SettingDetailDto settingDetailDto){
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        String email = SecurityUtil.getCurrentJwt().getEmail();
        return settingService.settingSave(userRole, email, settingDetailDto);
    }

//    @ApiOperation(value="관리자 환경설정 삭제", notes="관리자 환경설정 전체 삭제")
//    @PostMapping("/settingDelete")
//    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
//    public ResponseEntity<Map<String,Object>> settingDelete(){
//        String userRole = SecurityUtil.getCurrentJwt().getRole();
//        String email = SecurityUtil.getCurrentJwt().getEmail();
//        return settingService.settingDelete(userRole, email);
//    }
}

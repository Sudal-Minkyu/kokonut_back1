package com.app.kokonut.setting;


import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Joy
 * Date : 2022-01-05
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

}

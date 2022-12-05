package com.app.kokonut.admin;

import com.app.kokonut.admin.dtos.AdminGoogleOTPDto;
import com.app.kokonut.admin.jwt.dto.AuthRequestDto;
import com.app.kokonut.woody.common.AjaxResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Woody
 * Date : 2022-12-03
 * Time :
 * Remark :
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/Admin")
@RestController
public class AdminRestController {

    private final AjaxResponse res = new AjaxResponse();
    private final HashMap<String, Object> data = new HashMap<>();

    private final AdminService adminService;

    @GetMapping("/authorityCheck")
    @ApiOperation(value = "JWT토큰 테스트" , notes = "JWT 토큰이 유효한지 테스트하는 메서드")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> authorityCheck() {
        return adminService.authorityCheck();
    }

    // 사업자 호출
    @GetMapping("/masterTest")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> masterTest() {
        log.info("ROLE_MASTER TEST");
        return ResponseEntity.ok(res.success(data));
    }

    // 관리자 호출
    @GetMapping("/adminTest")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> adminTest() {
        log.info("ROLE_ADMIN TEST");
        return ResponseEntity.ok(res.success(data));
    }

    @PostMapping(value = "/otpVerify")
    @ApiOperation(value = "구글 OTP확인" , notes = "" +
            "1. Param 값으로 otpValue를 받는다." +
            "2. 해당 유저의 정보를 가져온다." +
            "3. otp 값을 체크한다." +
            "4. 성공이나 실패 값을 반환한다.")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> otpVerify(@RequestParam(value="otpValue", defaultValue="") String otpValue) {
        log.info("otpValue : "+otpValue);
        return adminService.otpVerify(otpValue);
    }

    @GetMapping(value = "/otpQRcode")
    @ApiOperation(value = "구글 QRCode 보내기 " , notes = "" +
            "1. JWT토큰의 값을 받는다" +
            "2. 해당 유저 이메일을 통해 QRCode값을 반환한다.")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> otpQRcode() {
        return adminService.otpQRcode();
    }

    @GetMapping(value = "/checkOTP")
    @ApiOperation(value = "구글 OTP 등록전 값 확인" , notes = "" +
            "1. Param 값으로 발급한 otpKey와 입력한 otpValue를 받는다." +
            "2. 두 값을 통해 성공여부를 반환한다.")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> checkOTP(@Validated AdminGoogleOTPDto.GoogleOtpCertification googleOtpCertification) {
        return adminService.checkOTP(googleOtpCertification);
    }

    @PostMapping(value = "/saveOTP")
    @ApiOperation(value = "구글 OTP 등록" , notes = "" +
            "1. 인증된 OTP값과 Key,Value+현재 비밀번호를 받는다." +
            "2. 받은 값을 통해 체크한다." +
            "3. 체크된 OTP를 등록한다.")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> saveOTP(@Validated AdminGoogleOTPDto.GoogleOtpSave googleOtpSave) {
        return adminService.saveOTP(googleOtpSave);
    }




}

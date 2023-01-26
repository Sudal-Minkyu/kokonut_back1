package com.app.kokonut.auth;

import com.app.kokonut.auth.dtos.AdminGoogleOTPDto;
import com.app.kokonut.auth.jwt.dto.AuthRequestDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-12-01
 * Time :
 * Remark : 로그인, 회원가입 등 JWT토큰 필요없이 호출할 수 있는 컨트롤러
 */
@Slf4j
@RequestMapping("/v0/api/Auth")
@RestController
public class AuthRestController {

    private final AuthService authService;

    @Autowired
    public AuthRestController(AuthService authService){
        this.authService = authService;
    }

    // 회원가입
    @PostMapping(value = "/signUp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "사업자 회원가입" , notes = "" +
            "1. Param 값으로 유저정보와 기업정보를 받는다." +
            "2. 유니크 값 중복체크를 한다." +
            "3. 기업정보를 저장한다." +
            "4. 사업자등록증, KMS인증키 등을 발급받는다.")
    public ResponseEntity<Map<String,Object>> signUp(@Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @Validated AuthRequestDto.SignUp signUp, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("사업자 회원가입 API 호출");
        return authService.signUp(signUp, request, response);
    }

    // 로그인 성공 이후 JWT Token 발급 및 업데이트
    // "loginVerify" + "/otpVerify" 합쳐진 메서드
    @PostMapping("/authToken")
    @ApiOperation(value = "로그인 - 로그인 성공후 JWT 토큰 발급" , notes = "JWT 엑세스토큰과 리플레쉬토큰을 발급해준다.")
    public ResponseEntity<Map<String,Object>> authToken(@Validated AuthRequestDto.Login login) {
        log.info("로그인한 이메일 : "+login.getKnEmail());
        return authService.authToken(login);
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 새로고침" , notes = "" +
            "1. Param 값으로 accessToken, refreshToken을 받는다." +
            "2. 해당 리플레쉬 토큰을 통해 새 토큰을 발급하며 이전의 토큰은 사용 불가처리를 한다.")
    public ResponseEntity<Map<String,Object>> reissue(@Validated AuthRequestDto.Reissue reissue) {
        return authService.reissue(reissue);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃 처리" , notes = "" +
            "1. Param 값으로 accessToken, refreshToken을 받는다." +
            "2. 받은 두 토큰을 검사하고 해당 토큰을 삭제처리 한다.")
    public ResponseEntity<Map<String,Object>> logout(@Validated AuthRequestDto.Logout logout) {
        return authService.logout(logout);
    }

    @GetMapping(value = "/otpQRcode")
    @ApiOperation(value = "구글 QRCode 보내기 " , notes = "" +
            "1. 로그인한 이메일 값을 받는다" +
            "2. 해당 유저 이메일을 통해 QRCode값을 반환한다.")
    public ResponseEntity<Map<String,Object>> otpQRcode(@RequestParam(value="email") String email) {
        return authService.otpQRcode(email);
    }

    @GetMapping(value = "/checkOTP")
    @ApiOperation(value = "구글 OTP 등록전 값 확인" , notes = "" +
            "1. Param 값으로 발급한 otpKey와 입력한 otpValue를 받는다." +
            "2. 두 값을 통해 성공여부를 반환한다.")
    public ResponseEntity<Map<String,Object>> checkOTP(@Validated AdminGoogleOTPDto.GoogleOtpCertification googleOtpCertification) {
        return authService.checkOTP(googleOtpCertification);
    }

    @PostMapping(value = "/saveOTP")
    @ApiOperation(value = "구글 OTP 등록" , notes = "" +
            "1. 인증된 OTP값과 Key,Value+ 이메일, 비밀번호를 받는다." +
            "2. 받은 값을 통해 체크한다." +
            "3. 체크된 OTP를 등록한다.")
    public ResponseEntity<Map<String,Object>> saveOTP(@Validated AdminGoogleOTPDto.GoogleOtpSave googleOtpSave) {
        return authService.saveOTP(googleOtpSave);
    }






















}

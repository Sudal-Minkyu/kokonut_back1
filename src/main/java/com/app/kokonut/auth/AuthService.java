package com.app.kokonut.auth;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.auth.dtos.AdminGoogleOTPDto;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.admin.entity.enums.AuthorityRole;
import com.app.kokonut.auth.jwt.been.JwtTokenProvider;
import com.app.kokonut.auth.jwt.config.GoogleOTP;
import com.app.kokonut.auth.jwt.dto.AuthRequestDto;
import com.app.kokonut.auth.jwt.dto.AuthResponseDto;
import com.app.kokonut.auth.jwt.dto.GoogleOtpGenerateDto;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import com.app.kokonut.woody.common.component.AriaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author Woody
 * Date : 2022-12-09
 * Time :
 * Remark : AuthService(인증서비스), 기존의 LoginService와 동일
 */
@Slf4j
@Service
public class AuthService {

    private final AjaxResponse res = new AjaxResponse();
    private final HashMap<String, Object> data = new HashMap<>();

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final StringRedisTemplate redisTemplate;
    private final GoogleOTP googleOTP;

    @Autowired
    public AuthService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, StringRedisTemplate redisTemplate, GoogleOTP googleOTP) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.redisTemplate = redisTemplate;
        this.googleOTP = googleOTP;
    }

    // 회원가입 기능
    public ResponseEntity<Map<String,Object>> signUp(AuthRequestDto.SignUp signUp) {
        log.info("signUp 호출");

        if (adminRepository.existsByEmail(signUp.getEmail())) {
            log.error("이미 회원가입된 이메일입니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO005.getCode(), ResponseErrorCode.KO005.getDesc()));
        }

        Admin admin = Admin.builder()
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .roleName(AuthorityRole.ROLE_MASTER)
                .regdate(LocalDateTime.now())
                .build();

        adminRepository.save(admin);

        return ResponseEntity.ok(res.success(data));
    }

    // 로그인, 구글OTP 확인후 -> JWT 발급기능
    public ResponseEntity<Map<String,Object>> authToken(AuthRequestDto.Login login) {
        log.info("authToken 호출");

        // 이메일이 test@kokonut.me 체크하는 로직추가
        String email = login.getEmail();

        if (email.equals("test@kokonut.me") || email.equals("test2@kokonut.me")) {
            // 여기부터 우디(Wooody)가 작성
            System.out.println("체험하기 모드 : 체험하기 로그인");

            data.put("jwtToken", "체험하기JWT");
            return ResponseEntity.ok(res.success(data));
        }
        else {
            if(login.getOtpValue() == null) {
                log.error("구글 OTP 값이 존재하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO010.getCode(),ResponseErrorCode.KO010.getDesc()));
            }

            Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);

            if (optionalAdmin.isEmpty()) {
                log.error("해당 유저가 존재하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(),"해당 유저가 "+ResponseErrorCode.KO004.getDesc()));
            }
            else {
                try {
                    // Login ID/PW 를 기반으로 Authentication 객체 생성 -> 아아디 / 비번 검증
                    // 이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
                    UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();

                    if(optionalAdmin.get().getOtpKey() == null){
                        log.error("등록된 OTP가 존재하지 않습니다. 구글 OTP 2단계 인증을 등록해주세요.");
                        return ResponseEntity.ok(res.fail(ResponseErrorCode.KO011.getCode(),ResponseErrorCode.KO011.getDesc()));
                    }
                    else {

                        // OTP 검증 절차
                        boolean auth = googleOTP.checkCode(login.getOtpValue(), optionalAdmin.get().getOtpKey());
                        log.info("auth : " + auth);

                        if (!auth) {
                            log.error("입력된 구글 OTP 값이 일치하지 않습니다. 확인해주세요.");
                            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO012.getCode(), ResponseErrorCode.KO012.getDesc()));
                        } else {
                            log.info("OTP인증완료 -> JWT토큰 발급");


                            // 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
                            // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
                            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

                            // 인증 정보를 기반으로 JWT 토큰 생성
                            AuthResponseDto.TokenInfo jwtToken = jwtTokenProvider.generateToken(authentication);

                            data.put("jwtToken", jwtToken);

                            // RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
                            redisTemplate.opsForValue().set("RT: " + authentication.getName(), jwtToken.getRefreshToken(), jwtToken.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

                            /* 해외 아이피 차단 여부 */
//                            loginService.ResetPwdError(user.getIdx()); 패스워드에러카운트 리셋

                            return ResponseEntity.ok(res.success(data));
                        }
                    }
                }
                catch (BadCredentialsException e) {
                    log.error("아이디 또는 비밀번호가 일치하지 않습니다.");
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO016.getCode(),ResponseErrorCode.KO016.getDesc()));
                }
            }
        }
    }

    // JWT 토큰 새로고침 기능
    public ResponseEntity<Map<String,Object>> reissue(AuthRequestDto.Reissue reissue) {
        log.info("reissue 호출");

        // Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            log.error("유효하지 않은 토큰정보임");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO007.getCode(),ResponseErrorCode.KO007.getDesc()));
        }

        // Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = redisTemplate.opsForValue().get("RT: "+authentication.getName());

        // 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if(ObjectUtils.isEmpty(refreshToken)) {
            log.error("로그아웃된 토큰임");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO006.getCode(),ResponseErrorCode.KO006.getDesc()));
        }

        if(!refreshToken.equals(reissue.getRefreshToken())) {
            log.error("Redis토큰과 받은 리플레쉬 토큰이 맞지않음");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO008.getCode(),ResponseErrorCode.KO008.getDesc()));
        }

        // Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(reissue.getAccessToken());
        redisTemplate.opsForValue().set(reissue.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        // 새로운 토큰 생성
        AuthResponseDto.TokenInfo jwtToken = jwtTokenProvider.generateToken(authentication);

        // RefreshToken Redis 업데이트
        redisTemplate.opsForValue().set("RT: "+authentication.getName(), jwtToken.getRefreshToken(), jwtToken.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        data.put("jwtToken", jwtToken);

        return ResponseEntity.ok(res.success(data));
    }

    // 로그아웃 기능
    public ResponseEntity<Map<String,Object>> logout(AuthRequestDto.Logout logout) {
        log.info("logout 호출");

        // Access Token 검증
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            log.error("유효하지 않은 토큰정보임");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO006.getCode(),ResponseErrorCode.KO006.getDesc()));
        }

        // Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());

        // Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT: "+authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT: "+authentication.getName());
        }

        // 해당 Access Token 유효시간 가지고 와서 BlackList로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue().set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return ResponseEntity.ok(res.success(data));
    }

    // 구글 QR코드 생성(1번)
    public ResponseEntity<Map<String, Object>> otpQRcode(String email) {
        log.info("otpQRcode 호출");

//        String userEmail = SecurityUtil.getCurrentUserEmail();
//        if(userEmail.equals("anonymousUser")){
//            log.error("사용하실 수 없는 토큰정보 입니다. 다시 로그인 해주세요.");
//            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO009.getCode(),ResponseErrorCode.KO009.getDesc()));
//        } else {

        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);

        if (optionalAdmin.isEmpty()) {
            log.error("해당 유저가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(),"해당 유저가 "+ResponseErrorCode.KO004.getDesc()));
        }
        else {
            log.info("구글 QR코드 보낼 이메일 : " + email);

            // 서프의 Map관련 이슈
//            HashMap<String, String> map = googleOTP.generate(userEmail);
            GoogleOtpGenerateDto googleOtpGenerateDto = googleOTP.generate(email);
//            String otpKey = map.get("encodedKey");
            String url = googleOtpGenerateDto.getUrl().replace("200x200", "160x160");

//            url = url.replace("200x200", "160x160");

            data.put("CSRF_TOKEN", UUID.randomUUID().toString());
            data.put("otpKey", googleOtpGenerateDto.getOtpKey());
            data.put("url", url);

            return ResponseEntity.ok(res.success(data));
        }
    }

    // 구글 OTP 값 확인(2번)
    public ResponseEntity<Map<String,Object>> checkOTP(AdminGoogleOTPDto.GoogleOtpCertification googleOtpCertification) {
        log.info("checkOTP 호출");

        Pattern pattern = Pattern.compile("[+-]?\\d+");
        if(!pattern.matcher(googleOtpCertification.getOtpValue()).matches()) {
            log.error("OTP는 숫자 형태로 입력하세요.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO015.getCode(),ResponseErrorCode.KO015.getDesc()));
        }

        boolean auth;
        auth = googleOTP.checkCode(googleOtpCertification.getOtpValue(), googleOtpCertification.getOtpKey());


        if(!auth){
            log.error("입력된 구글 OTP 값이 일치하지 않습니다. 확인해주세요.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO012.getCode(),ResponseErrorCode.KO012.getDesc()));
        }
        else {
            data.put("auth", true);
            return ResponseEntity.ok(res.success(data));
        }

    }

    // 구글 OTPKey 등록(3번)
    public ResponseEntity<Map<String, Object>> saveOTP(AdminGoogleOTPDto.GoogleOtpSave googleOtpSave) {
        log.info("saveOTP 호출");

        Optional<Admin> optionalAdmin = adminRepository.findByEmail(googleOtpSave.getEmail());

        if (optionalAdmin.isEmpty()) {
            log.error("해당 유저가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(),"해당 유저가 "+ResponseErrorCode.KO004.getDesc()));
        }
        else {

            log.info("OTP Key 등록 할 이메일 : " + optionalAdmin.get().getEmail());

            //현재암호비교
            if (!passwordEncoder.matches(googleOtpSave.getPassword(), optionalAdmin.get().getPassword())){
                log.error("입력한 비밀번호가 일치하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO013.getCode(), ResponseErrorCode.KO013.getDesc()));
            }

            String authOtpKey = googleOtpSave.getAuthOtpKey();
            AriaUtil aria = new AriaUtil();
            String decValue = aria.Decrypt(authOtpKey);
            System.out.println("decValue : "+decValue);

            if (!decValue.equals("authOtpKey11")) { // 본인인증 이후 나오는 값
                log.error("인증되지 않은 절차로 진행되었습니다. 올바른 방법으로 진행해주세요.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO013.getCode(), ResponseErrorCode.KO013.getDesc()));
            }

            optionalAdmin.get().setModifierIdx(optionalAdmin.get().getModifierIdx());
            optionalAdmin.get().setModifierName(optionalAdmin.get().getName());
            optionalAdmin.get().setModifyDate(LocalDateTime.now());

            optionalAdmin.get().setOtpKey(googleOtpSave.getOtpKey());
            adminRepository.save(optionalAdmin.get());

            // 변경이후 시스템관리자에게 메일보내기 시작 추가하기 12/05 Woody

            return ResponseEntity.ok(res.success(data));
        }
    }

}

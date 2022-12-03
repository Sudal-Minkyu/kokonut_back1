package com.app.kokonut.admin;

import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.admin.entity.enums.AuthorityRole;
import com.app.kokonut.admin.jwt.been.JwtTokenProvider;
import com.app.kokonut.admin.jwt.dto.AuthRequestDto;
import com.app.kokonut.admin.jwt.dto.AuthResponseDto;
import com.app.kokonut.admin.jwt.util.SecurityUtil;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Woody
 * Date : 2022-12-01
 * Time :
 * Remark : AdminService + 인증서비스
 */
@Slf4j
@Service
public class AdminService {

    private final AjaxResponse res = new AjaxResponse();
    private final HashMap<String, Object> data = new HashMap<>();

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, StringRedisTemplate redisTemplate){
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.redisTemplate = redisTemplate;
    }

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
                .regdate(new Date(System.currentTimeMillis()))
                .build();

        adminRepository.save(admin);

        return ResponseEntity.ok(res.success(data));
    }

    public ResponseEntity<Map<String,Object>> authToken(AuthRequestDto.Login login) {
        log.info("authToken 호출");

        if (adminRepository.findByEmail(login.getEmail()).orElse(null) == null) {
            log.error("해당하는 유저가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(),"해당하는 유저가 "+ResponseErrorCode.KO004.getDesc()));
        }

        // Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();

        // 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 토큰 생성
        AuthResponseDto.TokenInfo jwtToken = jwtTokenProvider.generateToken(authentication);

        data.put("jwtToken", jwtToken);

        // RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        redisTemplate.opsForValue().set("RT: " + authentication.getName(), jwtToken.getRefreshToken(), jwtToken.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return ResponseEntity.ok(res.success(data));
    }

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

    public ResponseEntity<Map<String,Object>> authorityCheck() {
        log.info("authorityCheck 호출");

        // SecurityContext에 담겨 있는 authentication userEamil 정보
        String userEmail = SecurityUtil.getCurrentUserEmail();

        if(userEmail.equals("anonymousUser")){
            log.info("사용할 수 없는 토큰입니다.");
        } else{
            log.info("관리자로 바꿀 이메일 : "+userEmail);

            Admin admin = adminRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

            log.info("해당 유저의 권한 : "+admin.getRoleName());

            // add ROLE_ADMIN
//            admin.getRoles().add(AuthorityRole.ROLE_ADMIN.name());
//            adminRepository.save(admin);
        }

        return ResponseEntity.ok(res.success(data));
    }

}

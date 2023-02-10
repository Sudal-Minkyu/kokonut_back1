package com.app.kokonut.auth.jwt.been;

import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Woody
 * Date : 2022-12-01
 * Time :
 * Remark : JWT Filter
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_TYPE = "Authorization";

    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("JwtAuthenticationFilter 호출");

        String token = resolveToken(request);

        if(token != null) {
            log.info("필터 거쳐감 Jwt Access Token : "+token);

            if(token.equals("kokonut")) {
                log.info("로그인 이후 이용해주세요.");
                throw new RuntimeException("로그인 이후 이용해주세요.");
            } else {
                // validateToken으로 토큰 유효성 검사 + ApiKey 유효성 검사
                int result = jwtTokenProvider.validateToken(token);
                if (result == 200) {
                    // Redis 에 해당 accessToken logout 여부 확인
                    String isLogout = redisTemplate.opsForValue().get(token);
                    if (ObjectUtils.isEmpty(isLogout)) {
                        // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
                        Authentication authentication = jwtTokenProvider.getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else if(result == 901) {
                    throw new io.jsonwebtoken.security.SecurityException("잘못된 JWT 토큰입니다.");
                } else if(result == 902) {
                    throw new RuntimeException("만료된 JWT 토큰입니다.");
                } else if(result == 903) {
                    throw new UnsupportedJwtException("지원되지 않는 JWT 토큰입니다.");
                } else if(result == 904) {
                    throw new IllegalArgumentException("JWT 토큰이 맞지 않습니다.");
                } else {
                    throw new RuntimeException("인증된 정보가 없습니다.");
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        return request.getHeader(BEARER_TYPE);
    }

}


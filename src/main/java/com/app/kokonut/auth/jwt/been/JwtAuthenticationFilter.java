package com.app.kokonut.auth.jwt.been;

import com.app.kokonut.apiKey.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

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

    private static final String APIKEY_TYPE = "ApiKey";

    private ApiKeyService apiKeyService;

    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("JwtAuthenticationFilter 여기왔다감");

        // 해더에서 JWT 토큰 추출
//        String token = null;
//        Cookie[] cookies = request.getCookies();
//        if(cookies != null) {
//            for(Cookie cookie : cookies) {
//                if (cookie.getName().equals("accessToken")) {
//                    token = cookie.getValue();
//                }
//            }
//        }
//        if(token == null) {
        String token = resolveToken(request);
//        }
        log.info("필터 거쳐감 Jwt Access Token : "+token);

//        if(token == null) {
//            log.info("토큰이 만료되었습니다.");
//        }

        String apikey = resolveApiKey(request);
        log.info("필터 거쳐감 ApiKey : "+apikey);

        // validateToken으로 토큰 유효성 검사 + ApiKey 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token) ) {
            // Redis 에 해당 accessToken logout 여부 확인
            String isLogout = redisTemplate.opsForValue().get(token);
            if (ObjectUtils.isEmpty(isLogout)) {
                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        return request.getHeader(BEARER_TYPE);
    }

    // Request Header 에서 APIKEY 정보 추출
    private String resolveApiKey(HttpServletRequest request) {
        return request.getHeader(APIKEY_TYPE);
    }

}


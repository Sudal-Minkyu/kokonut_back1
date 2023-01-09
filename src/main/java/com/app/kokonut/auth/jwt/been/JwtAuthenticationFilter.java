package com.app.kokonut.auth.jwt.been;

import com.app.kokonut.apiKey.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Woody
 * Date : 2022-12-01
 * Time :
 * Remark : JWT Filter
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

//    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer";

    private static final String APIKEY_TYPE = "ApiKey";

    private ApiKeyService apiKeyService;

    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Request Header 에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);
        log.info("필터 거쳐감 Jwt Access Token : "+token);

        String apikey = resolveApiKey((HttpServletRequest) request);
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
        chain.doFilter(request, response);
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


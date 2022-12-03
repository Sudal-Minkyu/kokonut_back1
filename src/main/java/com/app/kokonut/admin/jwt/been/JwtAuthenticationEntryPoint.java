package com.app.kokonut.admin.jwt.been;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Woody
 * Date : 2022-12-01
 * Remark :
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.info("인증되지 않은 사용자가 접근시 막아주는 핸들러 작동");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

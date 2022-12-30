package com.app.kokonut.auth.jwt.util;

import com.app.kokonut.auth.jwt.dto.JwtFilterDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * @author Woody
 * Date : 2022-12-01
 * Remark :
 */
public class SecurityUtil {

    // JWT 토큰을 통해 해당 유저의 이메일을 반환하는 메서드
    public static JwtFilterDto getCurrentJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("인증된 정보가 없습니다.");
        }

        return JwtFilterDto
                .builder()
                .email(authentication.getName())
                .role(authentication.getAuthorities().toString())
                .build();
    }

}

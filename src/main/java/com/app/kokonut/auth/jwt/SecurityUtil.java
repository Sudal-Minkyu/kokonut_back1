package com.app.kokonut.auth.jwt;

import com.app.kokonut.apiKey.dtos.ApiKeyInfoDto;
import com.app.kokonut.auth.jwt.dto.JwtFilterDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author Woody
 * Date : 2022-12-01
 * Remark :
 */
@Slf4j
public class SecurityUtil {

    // JWT 토큰을 통해 해당 유저의 이메일을 반환하는 메서드
    public static JwtFilterDto getCurrentJwt() {
        log.info("SecurityUtil.getCurrentJwt 호출");
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

    // "/v2/api/**"로 호출할 경우 -> JWT토큰 또는 ApiKey를 통해 해당 유저의 이메일을 반환한다.
    // JWT토큰으로 호출 할 경우 해당 유저의 Email정보를 반환하고 ApiKey으로 호출 할 경우 ApiKey의 사업자이메일을 반환한다.
    public static JwtFilterDto getCurrentJwtOrApiKey(HttpServletRequest request) {
        log.info("SecurityUtil.getCurrentJwtOrApiKey 호출");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getName().equals("anonymousUser")) {
            ApiKeyInfoDto apiKeyInfoDto = (ApiKeyInfoDto)request.getAttribute("apiKeyInfoDto");
            if(apiKeyInfoDto != null) {
                log.info("API Key로 통신함");
                return JwtFilterDto
                        .builder()
                        .email(apiKeyInfoDto.getEmail())
                        .role("")
                        .build();
            }else {
                throw new RuntimeException("인증된 정보가 없습니다.");
            }
        } else {
            return JwtFilterDto
                    .builder()
                    .email(authentication.getName())
                    .role(authentication.getAuthorities().toString())
                    .build();
        }
    }


}

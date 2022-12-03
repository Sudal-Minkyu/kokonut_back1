package com.app.kokonut.admin.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Woody
 * Date : 2022-12-01
 * Time :
 * Remark : 토큰 반환Dto
 */
public class AuthResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TokenInfo {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Long refreshTokenExpirationTime;
    }
}

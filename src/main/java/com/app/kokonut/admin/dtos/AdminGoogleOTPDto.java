package com.app.kokonut.admin.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @author Woody
 * Date : 2022-12-05
 * Time :
 * Remark : 구글 OTP 체크용 Dto
 */
public class AdminGoogleOTPDto {

    @Getter
    @Setter
    public static class GoogleOtpCertification {

        @NotBlank(message = "구글OTP 값은 필수 입력 값입니다.")
        private String otpValue;

        @NotBlank(message = "구글OTP Key 값은 필수 값입니다.")
        private String otpKey;

    }

    @Getter
    @Setter
    public static class GoogleOtpSave {

        @NotBlank(message = "구글OTP 값은 필수 입력 값입니다.")
        private String otpValue;

        @NotBlank(message = "구글Key 값은 필수 값입니다.")
        private String otpKey;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        private String password;

        @NotBlank(message = "인증 구글OTP 값은 필수 값입니다.")
        private String authOtpKey;

    }

}

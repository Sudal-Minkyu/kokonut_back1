package com.app.kokonut.auth.jwt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Woody
 * Date : 2022-12-01
 * Time :
 * Remark : 로그인, 회원가입, 로그아웃, 토큰재발급 응답 Dto
 */
public class AuthRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SignUp {

        @NotBlank(message = "이메일은 필수 입력값 입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String knEmail;

        @NotBlank(message = "회사명은 필수 입력값 입니다.")
        private String cpName;

        @NotBlank(message = "대표자명은 필수 입력값 입니다.")
        private String cpRepresentative;

        @NotBlank(message = "이름은 필수 입력값 입니다.")
        private String knName;

        @NotBlank(message = "핸드폰번호는 필수 입력값 입니다.")
        private String knPhoneNumber;

        @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String knPassword;

        private String knPasswordConfirm; // 비밀번호 체크

        @NotBlank(message = "사업자등록번호는 필수 입력값 입니다.")
        private String cpBusinessNumber;

        private MultipartFile multipartFile; // 사업자등록등 -> 기존 Kokonut에선 file이라는 변수로 받아옴.

        @NotBlank(message = "업태/업종은 필수 입력값 입니다.")
        private String cpBusinessType;

        @NotBlank(message = "사업자 전화번호는 필수 입력값 입니다.")
        private String cpTel;

        @NotBlank(message = "사업자 우편번호는 필수 입력값 입니다.")
        private String cpAddressNumber;

        @NotBlank(message = "기업주소는 필수 입력값 입니다.")
        private String cpAddress;

        private String cpAddressDetail;

        @Builder
        public SignUp(String knEmail, String cpName, String cpRepresentative, String knName, String knPhoneNumber, String knPassword,
                      String knPasswordConfirm, String cpBusinessNumber, MultipartFile multipartFile, String cpBusinessType, String cpTel,
                      String cpAddressNumber, String cpAddress, String cpAddressDetail) {
            this.knEmail = knEmail;
            this.cpName = cpName;
            this.cpRepresentative = cpRepresentative;
            this.knName = knName;
            this.knPhoneNumber = knPhoneNumber;
            this.knPassword = knPassword;
            this.knPasswordConfirm = knPasswordConfirm;
            this.cpBusinessNumber = cpBusinessNumber;
            this.multipartFile = multipartFile;
            this.cpBusinessType = cpBusinessType;
            this.cpTel = cpTel;
            this.cpAddressNumber = cpAddressNumber;
            this.cpAddress = cpAddress;
            this.cpAddressDetail = cpAddressDetail;
        }
    }

    @Getter
    @Setter
    public static class Login {

        private String otpValue;

        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String knEmail;

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        private String knPassword;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(knEmail, knPassword);
        }

    }

    @Getter
    @Setter
    public static class Reissue {
        @NotBlank(message = "accessToken을 입력해주세요.")
        private String accessToken;

        @NotBlank(message = "refreshToken을 입력해주세요.")
        private String refreshToken;
    }

    @Getter
    @Setter
    public static class Logout {
        @NotBlank(message = "잘못된 요청입니다.")
        private String accessToken;

        @NotBlank(message = "잘못된 요청입니다.")
        private String refreshToken;
    }

}
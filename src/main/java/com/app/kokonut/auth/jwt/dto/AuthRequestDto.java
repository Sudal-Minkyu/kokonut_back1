package com.app.kokonut.auth.jwt.dto;

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
        private String email;

        @NotBlank(message = "회사명은 필수 입력값 입니다.")
        private String companyName;

        @NotBlank(message = "대표자명은 필수 입력값 입니다.")
        private String representative;

        @NotBlank(message = "이름은 필수 입력값 입니다.")
        private String name;

        @NotBlank(message = "핸드폰번호는 필수 입력값 입니다.")
        private String phoneNumber;

        @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        private String passwordConfirm; // 비밀번호 체크

        @NotBlank(message = "사업자등록번호는 필수 입력값 입니다.")
        private String businessNumber;

        @NotBlank(message = "사업자등록증은 필수입니다.")
        private MultipartFile multipartFile; // 사업자등록등 -> 기존 Kokonut에선 file이라는 변수로 받아옴.

        @NotBlank(message = "업태/업종은 필수 입력값 입니다.")
        private String businessType;

        @NotBlank(message = "사업자 전화번호는 필수 입력값 입니다.")
        private String companyTel;

        @NotBlank(message = "사업자 우편번호는 필수 입력값 입니다.")
        private String companyAddressNumber;

        @NotBlank(message = "기업주소는 필수 입력값 입니다.")
        private String companyAddress;

        private String companyAddressDetail;

    }

    @Getter
    @Setter
    public static class Login {

        private String otpValue;

        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
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
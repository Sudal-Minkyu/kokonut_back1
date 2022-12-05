package com.app.kokonut.woody.common;

/**
 * @author Woody
 * Date : 2022-11-07
 * Time :
 * Remark : 에러 응답코드
 */
public enum ResponseErrorCode {
    KO001("KO001", "접근권한이 없습니다."),
    KO002("KO002", "조회할 수 없습니다."),
    KO003("KO003", "조회한 데이터가 없습니다."),
    KO004("KO004", "존재하지 않습니다."),
    KO005("KO005", "이미 회원가입된 이메일입니다."),
    KO006("KO006", "유효하지 않은 토큰정보입니다."),
    KO007("KO007", "Refresh Token 정보가 유효하지 않습니다."),
    KO008("KO008", "Refresh Token 정보가 일치하지 않습니다."),
    KO009("KO009", "사용하실 수 없는 토큰정보 입니다. 다시 로그인 해주세요."),
    KO010("KO010", "구글 OTP 값이 존재하지 않습니다."),
    KO011("KO011", "등록된 OTP가 존재하지 않습니다. 구글 OTP 2단계 인증을 등록해주세요."),
    KO012("KO012", "구글 OTP 값이 일치하지 않습니다. 확인해주세요."),
    KO013("KO013", "입력한 비밀번호가 일치하지 않습니다."),
    KO014("KO014", "인증되지 않은 절차로 진행되었습니다. 올바른 방법으로 진행해주세요."),
    KO015("KO015", "OTP는 숫자 형태로 입력하세요."),
    ;

    private String code;
    private String desc;

    ResponseErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

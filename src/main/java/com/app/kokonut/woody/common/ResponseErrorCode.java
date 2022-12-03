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

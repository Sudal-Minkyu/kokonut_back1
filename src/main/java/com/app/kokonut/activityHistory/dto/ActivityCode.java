package com.app.kokonut.activityHistory.dto;

/**
 * @author Woody
 * Date : 2023-01-09
 * Time :
 * Remark : 사용자 활동 Enum
 */
public enum ActivityCode {

    AC_01("AC_01", "로그인"),
    AC_02("AC_02", "회원정보 변경"),
    AC_03("AC_03", "회원삭제"),
    AC_04("AC_04", "관리자 추가"),
    AC_05("AC_05", "관리자 권한 변경"),
    AC_06("AC_06", "열람이력 다운로드"),
    AC_07("AC_07", "활동이력 다운로드"),
    AC_08("AC_08", "고객정보 열람"),
    AC_09("AC_09", "고객정보 다운로드"),
    AC_10("AC_10", "고객정보 처리"),
    AC_11("AC_11", "회원정보 DB관리 변경"),
    AC_12("AC_12", "회원테이블 DB항목 변경"),
    AC_13("AC_13", "회원등록"),
    AC_14("AC_14", "정보제공 목록"),
    AC_15("AC_15", "정보 파기 관리"),
    AC_16("AC_16", "회원테이블 생성"),
    AC_17("AC_17", "전체 DB다운로드"),
    AC_18("AC_18", "회원 관리 변경"),
    AC_19("AC_19", "회원테이블 DB 항목 추가"),
    AC_20("AC_20", "회원테이블 DB 항목 수정"),
    AC_21("AC_21", "회원테이블 DB 항목 삭제"),
    AC_22("AC_22", "개인정보 전체 DB 데이터 다운로드 요청"),
    AC_23("AC_23", "개인정보 전체 DB 데이터 다운로드 시작"),
    AC_24("AC_24", "API KEY 발급"),
    AC_25("AC_25", "API KEY 재발급"),
    AC_26("AC_26", "API KEY 사용 차단"),
    AC_27("AC_27", "API KEY 사용 차단 해제"),
    ;
    
    private final String code;
    private final String desc;

    ActivityCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}

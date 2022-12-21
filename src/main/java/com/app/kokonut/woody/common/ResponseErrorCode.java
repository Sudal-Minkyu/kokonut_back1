package com.app.kokonut.woody.common;

/**
 * @author Woody
 * Date : 2022-11-07
 * Time :
 * Remark : 에러 응답코드
 */
public enum ResponseErrorCode {

    KO000("KO000", "체험하기모드는 이용할 수 없습니다."),
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
    KO012("KO012", "입력된 구글 OTP 값이 일치하지 않습니다. 확인해주세요."),
    KO013("KO013", "입력한 비밀번호가 일치하지 않습니다."),
    KO014("KO014", "인증되지 않은 절차로 진행되었습니다. 올바른 방법으로 진행해주세요."),
    KO015("KO015", "OTP는 숫자 형태로 입력하세요."),
    KO016("KO016", "아이디 또는 비밀번호가 일치하지 않습니다."),
    KO017("KO017", "채널ID를 입력해주세요."),
    KO018("KO018", "전화번호를 입력해주세요."),
    KO019("KO019", "인증번호를 입력해주세요."),
    KO020("KO020", "카카오 채널 삭제를 실패했습니다."),
    KO021("KO021", "404 에러 NCP에서 템플릿 삭제됬는지 확인해볼 것 to.woody"),
    KO022("KO022", "알림톡 템플릿 삭제를 실패했습니다."),
    KO023("KO023", "해당 알림톡 메세지를 찾을 수 없습니다."),
    KO024("KO024", "예약발송일 경우 보낼시간을 설정해주세요."),
    KO025("KO025", "발송할 수신자를 선택해주세요."),
    KO026("KO026", "알림톡 발송을 실패했습니다."),
    KO027("KO027", "알림톡메세지 결과정보가 존재하지 않습니다."),
    KO028("KO028", "예약발송 취소를 실패했습니다."),
    KO029("KO029", "해당 친구톡 메세지를 찾을 수 없습니다."),
    KO030("KO030", "친구톡 이미지 업로드를 실패했습니다."),

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

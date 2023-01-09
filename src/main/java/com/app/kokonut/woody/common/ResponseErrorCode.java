package com.app.kokonut.woody.common;

/**
 * @author Woody
 * Date : 2022-11-07
 * Time :
 * Remark : 에러 응답코드, 응답내용
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
    KO031("KO031", "이메일 상세정보 ID 값이 존재하지 않습니다."),
    KO032("KO032", "이메일 정보가 존재 하지 않습니다."),

    KO033("KO033", "본인인증으로 입력된 핸드폰 번호가 아닙니다. 본인인증을 완료해주세요."),
    KO034("KO034", "이미 회원가입된 핸드폰번호 입니다."),
    KO035("KO035", "이미 회원가입된 사업자등록번호 입니다."),
    KO036("KO036", "암호화 생성을 실패했습니다. 관리자에게 문의해주세요."),
    KO037("KO037", "입력한 비밀번호가 서로 일치하지 않습니다."),
    KO038("KO038", "사업자등록증을 업로드해주세요."),
    KO039("KO039", "이미지 업로드를 실패했습니다. 관리자에게 문의해주세요."),

    KO040("KO040", "이메일 받는 사람 유형이 입력되지 않았습니다. 관리자에게 문의해주세요."),
    KO041("KO041", "이메일 발송을 실패했습니다. 관리자에게 문의해주세요."),
    KO042("KO042", "이메일 그룹 ID 값이 존재하지 않습니다."),

    KO043("KO043", "암호화 키가 존재하지 않습니다."),
    KO044("KO044", "회원상태 여부를 선택해주세요."),
    KO045("KO045", "의 필드명은 존재하지 않습니다. 새로고침이후 다시 시도해주시길 바랍니다."),
    KO046("KO046", "이미 가입된 회원ID 입니다."),

    KO047("KO047", "공지사항 ID 값이 존재하지 않습니다."),
    KO048("KO048", "공지사항 정보가 존재 하지 않습니다."),

    KO049("KO049", "자주묻는 질문 ID 값이 존재하지 않습니다."),
    KO050("KO050", "자주묻는 질문 정보가 존재 하지 않습니다."),

    KO051("KO051", "개인정보 수집 및 이용 안내 ID 값이 존재하지 않습니다."),
    KO052("KO052", "개인정보 수집 및 이용 안내 정보가 존재 하지 않습니다."),

    KO053("KO053", "1:1문의 ID 값이 존재하지 않습니다."),
    KO054("KO054", "1:1문의 게시글 정보가 존재 하지 않습니다."),
    KO055("KO055", "본인이 작성한 1:1문의만 확인 가능합니다."),

    KO056("KO056", "파일 업로드를 실패했습니다. 관리자에게 문의해주세요."),

    KO057("KO057", "서비스 ID 값이 존재하지 않습니다."),

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

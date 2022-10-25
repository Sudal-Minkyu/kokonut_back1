package com.app.kokonut.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class AdminVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * COMPANY IDX
     */
    @ApiModelProperty("COMPANY IDX")
    private Integer companyIdx;


    /**
     * 마스터IDX(마스터는 0):관리자로 등록한 마스터의 키
     */
    @ApiModelProperty("마스터IDX(마스터는 0):관리자로 등록한 마스터의 키")
    private Integer masterIdx;


    /**
     * SYSTEM:0,나머지는 레벨 IDX
     */
    @ApiModelProperty("SYSTEM:0,나머지는 레벨 IDX")
    private Integer adminLevelIdx;


    /**
     * 회원타입(1:사업자,2:개인)
     */
    @ApiModelProperty("회원타입(1:사업자,2:개인)")
    private Integer userType;


    /**
     * 이메일
     */
    @ApiModelProperty("이메일")
    private String email;


    /**
     * 비밀번호
     */
    @ApiModelProperty("비밀번호")
    private String password;


    /**
     * 비밀번호 변경 일자
     */
    @ApiModelProperty("비밀번호 변경 일자")
    private Date pwdChangeDate;


    /**
     * 비밀번호오류횟수
     */
    @ApiModelProperty("비밀번호오류횟수")
    private Integer pwdErrorCount;


    /**
     * 이름(대표자명)
     */
    @ApiModelProperty("이름(대표자명)")
    private String name;


    /**
     * 휴대폰번호
     */
    @ApiModelProperty("휴대폰번호")
    private String phoneNumber;


    /**
     * 부서
     */
    @ApiModelProperty("부서")
    private String department;


    /**
     * 0:정지(권한해제),1:사용,2:로그인제한(비번5회오류),3:탈퇴, 4:휴면계정
     */
    @ApiModelProperty("0:정지(권한해제),1:사용,2:로그인제한(비번5회오류),3:탈퇴, 4:휴면계정")
    private Integer state;


    /**
     * 휴면계정 전환일
     */
    @ApiModelProperty("휴면계정 전환일")
    private Date dormantDate;


    /**
     * 계정삭제예정일
     */
    @ApiModelProperty("계정삭제예정일")
    private Date expectedDeleteDate;


    /**
     * 권한해제 사유
     */
    @ApiModelProperty("권한해제 사유")
    private String reason;


    /**
     * 최근 접속 IP
     */
    @ApiModelProperty("최근 접속 IP")
    private String ip;


    /**
     * 승인상태(1:승인대기, 2:승인완료, 3:승인보류)
     */
    @ApiModelProperty("승인상태(1:승인대기, 2:승인완료, 3:승인보류)")
    private Integer approvalState;


    /**
     * 관리자승인일시,반려일시
     */
    @ApiModelProperty("관리자승인일시,반려일시")
    private Date approvalDate;


    /**
     * 관리자 반려 사유
     */
    @ApiModelProperty("관리자 반려 사유")
    private String approvalReturnReason;


    /**
     * 승인자(반려자)
     */
    @ApiModelProperty("승인자(반려자)")
    private String approvalName;


    /**
     * 탈퇴사유선택(1:계정변경, 2:서비스이용불만,3:사용하지않음,4:기타)
     */
    @ApiModelProperty("탈퇴사유선택(1:계정변경, 2:서비스이용불만,3:사용하지않음,4:기타)")
    private Integer withdrawalReasonType;


    /**
     * 탈퇴사유
     */
    @ApiModelProperty("탈퇴사유")
    private String withdrawalReason;


    /**
     * 탈퇴일시
     */
    @ApiModelProperty("탈퇴일시")
    private Date withdrawalDate;


    /**
     * 최근접속일시(휴면계정전환에 필요)
     */
    @ApiModelProperty("최근접속일시(휴면계정전환에 필요)")
    private Date lastLoginDate;


    /**
     * 이메일인증여부
     */
    @ApiModelProperty("이메일인증여부")
    private String emailAuth;


    /**
     * 이메일인증번호
     */
    @ApiModelProperty("이메일인증번호")
    private String emailAuthNumber;


    /**
     * 비밀번호 설정 시 인증번호
     */
    @ApiModelProperty("비밀번호 설정 시 인증번호")
    private String pwdAuthNumber;


    /**
     * 인증시작시간
     */
    @ApiModelProperty("인증시작시간")
    private Date authStartDate;


    /**
     * 인증종료시간
     */
    @ApiModelProperty("인증종료시간")
    private Date authEndDate;


    /**
     * 구글 OTP에 사용될 KEY
     */
    @ApiModelProperty("구글 OTP에 사용될 KEY")
    private String otpKey;


    /**
     * GOOGLE인증여부
     */
    @ApiModelProperty("GOOGLE인증여부")
    private String loginAuth;


    /**
     * 권한(시스템관리자:ROLE_SYSTEM, 마스터관리자:ROLE_MASTER, 일반관리자 : ROLE_ADMIN)
     */
    @ApiModelProperty("권한(시스템관리자:ROLE_SYSTEM, 마스터관리자:ROLE_MASTER, 일반관리자 : ROLE_ADMIN)")
    private String roleName;


    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    private Date regdate;


    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    private Integer modifierIdx;


    /**
     * 수정자이름
     */
    @ApiModelProperty("수정자이름")
    private String modifierName;


    /**
     * 수정일시
     */
    @ApiModelProperty("수정일시")
    private Date modifyDate;

}

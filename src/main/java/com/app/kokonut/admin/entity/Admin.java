package com.app.kokonut.admin.entity;

import com.app.kokonut.admin.entity.enums.AuthorityRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@EqualsAndHashCode(of = "idx")
@Table(name="admin")
public class Admin implements UserDetails {

    /**
     * 키
     */
    @Id
    @ApiModelProperty("키")
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * COMPANY IDX
     */
    @Column(name = "COMPANY_IDX")
    @ApiModelProperty("COMPANY IDX")
    private Integer companyIdx;

    /**
     * 마스터IDX(마스터는 0):관리자로 등록한 마스터의 키
     */
    @Column(name = "MASTER_IDX")
    @ApiModelProperty("마스터IDX(마스터는 0):관리자로 등록한 마스터의 키")
    private Integer masterIdx;

    /**
     * 회원타입(1:사업자,2:개인)
     */
    @Column(name = "USER_TYPE")
    @ApiModelProperty("회원타입(1:사업자,2:개인)")
    private Integer userType;

    /**
     * 이메일
     */
    @Column(name = "EMAIL")
    @ApiModelProperty("이메일")
    private String email;

    /**
     * 비밀번호
     */
    @ApiModelProperty("비밀번호")
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 비밀번호 변경 일자
     */
    @ApiModelProperty("비밀번호 변경 일자")
    @Column(name = "PWD_CHANGE_DATE")
    private LocalDateTime pwdChangeDate;

    /**
     * 비밀번호오류횟수
     */
    @ApiModelProperty("비밀번호오류횟수")
    @Column(name = "PWD_ERROR_COUNT")
    private Integer pwdErrorCount = 0;

    /**
     * 이름(대표자명)
     */
    @Column(name = "NAME")
    @ApiModelProperty("이름(대표자명)")
    private String name;

    /**
     * 휴대폰번호
     */
    @ApiModelProperty("휴대폰번호")
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    /**
     * 부서
     */
    @ApiModelProperty("부서")
    @Column(name = "DEPARTMENT")
    private String department;

    /**
     * 0:정지(권한해제),1:사용,2:로그인제한(비번5회오류),3:탈퇴, 4:휴면계정
     */
    @Column(name = "STATE")
    @ApiModelProperty("0:정지(권한해제),1:사용,2:로그인제한(비번5회오류),3:탈퇴, 4:휴면계정")
    private Integer state = 1;

    /**
     * 휴면계정 전환일
     */
    @ApiModelProperty("휴면계정 전환일")
    @Column(name = "DORMANT_DATE")
    private LocalDateTime dormantDate;

    /**
     * 계정삭제예정일
     */
    @ApiModelProperty("계정삭제예정일")
    @Column(name = "EXPECTED_DELETE_DATE")
    private LocalDateTime expectedDeleteDate;

    /**
     * 권한해제 사유
     */
    @Column(name = "REASON")
    @ApiModelProperty("권한해제 사유")
    private String reason;

    /**
     * 최근 접속 IP
     */
    @Column(name = "IP")
    @ApiModelProperty("최근 접속 IP")
    private String ip;

    /**
     * 승인상태(1:승인대기, 2:승인완료, 3:승인보류)
     */
    @Column(name = "APPROVAL_STATE")
    @ApiModelProperty("승인상태(1:승인대기, 2:승인완료, 3:승인보류)")
    private Integer approvalState = 1;

    /**
     * 관리자승인일시,반려일시
     */
    @Column(name = "APPROVAL_DATE")
    @ApiModelProperty("관리자승인일시,반려일시")
    private LocalDateTime approvalDate;

    /**
     * 관리자 반려 사유
     */
    @ApiModelProperty("관리자 반려 사유")
    @Column(name = "APPROVAL_RETURN_REASON")
    private String approvalReturnReason;

    /**
     * 승인자(반려자)
     */
    @ApiModelProperty("승인자(반려자)")
    @Column(name = "APPROVAL_NAME")
    private String approvalName;

    /**
     * 탈퇴사유선택(1:계정변경, 2:서비스이용불만,3:사용하지않음,4:기타)
     */
    @Column(name = "WITHDRAWAL_REASON_TYPE")
    @ApiModelProperty("탈퇴사유선택(1:계정변경, 2:서비스이용불만,3:사용하지않음,4:기타)")
    private Integer withdrawalReasonType;

    /**
     * 탈퇴사유
     */
    @ApiModelProperty("탈퇴사유")
    @Column(name = "WITHDRAWAL_REASON")
    private String withdrawalReason;

    /**
     * 탈퇴일시
     */
    @ApiModelProperty("탈퇴일시")
    @Column(name = "WITHDRAWAL_DATE")
    private LocalDateTime withdrawalDate;

    /**
     * 최근접속일시(휴면계정전환에 필요)
     */
    @Column(name = "LAST_LOGIN_DATE")
    @ApiModelProperty("최근접속일시(휴면계정전환에 필요)")
    private LocalDateTime lastLoginDate;

    /**
     * 이메일인증여부
     */
    @ApiModelProperty("이메일인증여부")
    @Column(name = "IS_EMAIL_AUTH")
    private String emailAuth = "N";

    /**
     * 이메일인증번호
     */
    @ApiModelProperty("이메일인증번호")
    @Column(name = "EMAIL_AUTH_NUMBER")
    private String emailAuthNumber;

    /**
     * 비밀번호 설정 시 인증번호
     */
    @Column(name = "PWD_AUTH_NUMBER")
    @ApiModelProperty("비밀번호 설정 시 인증번호")
    private String pwdAuthNumber;

    /**
     * 인증시작시간
     */
    @ApiModelProperty("인증시작시간")
    @Column(name = "AUTH_START_DATE")
    private LocalDateTime authStartDate;

    /**
     * 인증종료시간
     */
    @ApiModelProperty("인증종료시간")
    @Column(name = "AUTH_END_DATE")
    private LocalDateTime authEndDate;

    /**
     * 구글 OTP에 사용될 KEY
     */
    @Column(name = "OTP_KEY")
    @ApiModelProperty("구글 OTP에 사용될 KEY")
    private String otpKey;

    /**
     * GOOGLE인증여부
     */
    @Column(name = "IS_LOGIN_AUTH")
    @ApiModelProperty("GOOGLE인증여부")
    private String loginAuth = "N";

    /**
     * 권한(시스템관리자:ROLE_SYSTEM, 관리자 : ROLE_ADMIN, 마스터관리자:ROLE_MASTER)
     */
    @Enumerated(EnumType.STRING)
    @ApiModelProperty("권한(시스템관리자:ROLE_SYSTEM, 관리자 : ROLE_ADMIN, 마스터관리자:ROLE_MASTER)")
    @Column(name="ROLE_NAME")
    private AuthorityRole roleName;

    /**
     * 등록일시
     */
    @Column(name = "REGDATE")
    @ApiModelProperty("등록일시")
    private LocalDateTime regdate;

    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    @Column(name = "MODIFIER_IDX")
    private Integer modifierIdx;

    /**
     * 수정자이름
     */
    @ApiModelProperty("수정자이름")
    @Column(name = "MODIFIER_NAME")
    private String modifierName;

    /**
     * 수정일시
     */
    @ApiModelProperty("수정일시")
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(roleName.getDesc()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

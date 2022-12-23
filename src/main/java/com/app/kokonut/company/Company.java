package com.app.kokonut.company;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EqualsAndHashCode(of = "idx")
@Data
@NoArgsConstructor
@Table(name="company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 주키
     */
    @Id
    @ApiModelProperty("주키")
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * 서비스
     */
    @ApiModelProperty("서비스")
    @Column(name = "SERVICE_IDX")
    private Integer serviceIdx;

    /**
     * 회사명
     */
    @ApiModelProperty("회사명")
    @Column(name = "COMPANY_NAME")
    private String companyName;

    /**
     * 대표자명
     */
    @ApiModelProperty("대표자명")
    @Column(name = "REPRESENTATIVE")
    private String representative;

    /**
     * 사업자등록번호
     */
    @ApiModelProperty("사업자등록번호")
    @Column(name = "BUSINESS_NUMBER")
    private String businessNumber;

    /**
     * 업태/업종
     */
    @ApiModelProperty("업태/업종")
    @Column(name = "BUSINESS_TYPE")
    private String businessType;

    /**
     * 사업장 전화번호
     */
    @Column(name = "COMPANY_TEL")
    @ApiModelProperty("사업장 전화번호")
    private String companyTel;

    /**
     * 우편번호(주소)
     */
    @ApiModelProperty("우편번호(주소)")
    @Column(name = "COMPANY_ADDRESS_NUMBER")
    private String companyAddressNumber;

    /**
     * 주소
     */
    @ApiModelProperty("주소")
    @Column(name = "COMPANY_ADDRESS")
    private String companyAddress;

    /**
     * 상세주소
     */
    @ApiModelProperty("상세주소")
    @Column(name = "COMPANY_ADDRESS_DETAIL")
    private String companyAddressDetail;

    /**
     * 상품(PREMIUM, STANDARD, BASIC)
     */
    @Column(name = "SERVICE")
    @ApiModelProperty("상품(PREMIUM, STANDARD, BASIC)")
    private String service;

    /**
     * 결제일(5일,10일 등 일자)
     */
    @Column(name = "PAY_DAY")
    @ApiModelProperty("결제일(5일,10일 등 일자)")
    private Integer payDay;

    /**
     * 결제등록일
     */
    @Column(name = "PAY_DATE")
    @ApiModelProperty("결제등록일")
    private Date payDate;

    /**
     * 카드이름
     */
    @ApiModelProperty("카드이름")
    @Column(name = "CARD_NAME")
    private String cardName;

    /**
     * 카드코드
     */
    @ApiModelProperty("카드코드")
    @Column(name = "CARD_CODE")
    private Integer cardCode;

    /**
     * 카드번호
     */
    @ApiModelProperty("카드번호")
    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    /**
     * 카드유효기간
     */
    @Column(name = "EXPIRY")
    @ApiModelProperty("카드유효기간")
    private String expiry;

    /**
     * 카드 생년월일(사업자등록번호)
     */
    @Column(name = "BIRTH")
    @ApiModelProperty("카드 생년월일(사업자등록번호)")
    private String birth;

    /**
     * 카드 비밀번호 앞 두자리 XX
     */
    @Column(name = "PWD_2DIGIT")
    @ApiModelProperty("카드 비밀번호 앞 두자리 XX")
    private String pwd2digit;

    /**
     * 카드(빌링키)와 1:1로 대응하는 값
     */
    @Column(name = "CUSTOMER_UID")
    @ApiModelProperty("카드(빌링키)와 1:1로 대응하는 값")
    private String customerUid;

    /**
     * 자동결제(1:자동결제안함, 2:첫결제신청, 3: 해제, 4:첫결제 이후 재결제, 6:강제해제)
     */
    @Column(name = "IS_AUTO_PAY")
    @ApiModelProperty("자동결제(1:자동결제안함, 2:첫결제신청, 3: 해제, 4:첫결제 이후 재결제, 6:강제해제)")
    private Integer autoPay;

    /**
     * 서비스 결제 X 강제 해지시 결제 안한 금액
     */
    @Column(name = "STOP_SERVICE_PRICE")
    @ApiModelProperty("서비스 결제 X 강제 해지시 결제 안한 금액")
    private Integer stopServicePrice;

    /**
     * 자동결제 해지일시
     */
    @ApiModelProperty("자동결제 해지일시")
    @Column(name = "NOT_AUTO_PAY_DATE")
    private Date notAutoPayDate;

    /**
     * 회원권 시작일
     */
    @ApiModelProperty("회원권 시작일")
    @Column(name = "VALID_START")
    private Date validStart;

    /**
     * 회원권 종료일
     */
    @Column(name = "VALID_END")
    @ApiModelProperty("회원권 종료일")
    private Date validEnd;

    /**
     * 결제요청UID
     */
    @ApiModelProperty("결제요청UID")
    @Column(name = "PAY_REQUEST_UID")
    private String payRequestUid;

    /**
     * 사업자등록증사본
     */
    @ApiModelProperty("사업자등록증사본")
    @Column(name = "FILE_GROUP_ID")
    private String fileGroupId;

    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    @Column(name = "ADMIN_IDX")
    private Integer adminIdx;

    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    @Column(name = "MODIFIER_IDX")
    private Integer modifierIdx;

    /**
     * 수정자 이름
     */
    @ApiModelProperty("수정자 이름")
    @Column(name = "MODIFIER_NAME")
    private String modifierName;

    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

    /**
     * 암호화한 키
     */
    @ApiModelProperty("암호화한 키")
    @Column(name = "ENCRYPT_TEXT")
    private String encryptText;

    /**
     * 복호화에 사용할 데이터 키
     */
    @Column(name = "DATA_KEY")
    @ApiModelProperty("복호화에 사용할 데이터 키")
    private String dataKey;

    /**
     * 누적 휴면회원 수
     */
    @ApiModelProperty("누적 휴면회원 수")
    @Column(name = "DORMANT_ACCUMULATE")
    private Integer dormantAccumulate;

}

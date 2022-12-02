package com.app.kokonut.refactor.company.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class CompanyQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 주키
     */
    @ApiModelProperty("주키")
    private Integer idx;


    /**
     * 서비스
     */
    @ApiModelProperty("서비스")
    private Integer serviceIdx;


    /**
     * 회사명
     */
    @ApiModelProperty("회사명")
    private String companyName;


    /**
     * 대표자명
     */
    @ApiModelProperty("대표자명")
    private String representative;


    /**
     * 사업자등록번호
     */
    @ApiModelProperty("사업자등록번호")
    private String businessNumber;


    /**
     * 업태/업종
     */
    @ApiModelProperty("업태/업종")
    private String businessType;


    /**
     * 사업장 전화번호
     */
    @ApiModelProperty("사업장 전화번호")
    private String companyTel;


    /**
     * 우편번호(주소)
     */
    @ApiModelProperty("우편번호(주소)")
    private String companyAddressNumber;


    /**
     * 주소
     */
    @ApiModelProperty("주소")
    private String companyAddress;


    /**
     * 상세주소
     */
    @ApiModelProperty("상세주소")
    private String companyAddressDetail;


    /**
     * 상품(PREMIUM, STANDARD, BASIC)
     */
    @ApiModelProperty("상품(PREMIUM, STANDARD, BASIC)")
    private String service;


    /**
     * 결제일(5일,10일 등 일자)
     */
    @ApiModelProperty("결제일(5일,10일 등 일자)")
    private Integer payDay;


    /**
     * 결제등록일
     */
    @ApiModelProperty("결제등록일")
    private Date payDate;


    /**
     * 카드이름
     */
    @ApiModelProperty("카드이름")
    private String cardName;


    /**
     * 카드코드
     */
    @ApiModelProperty("카드코드")
    private Integer cardCode;


    /**
     * 카드번호
     */
    @ApiModelProperty("카드번호")
    private String cardNumber;


    /**
     * 카드유효기간
     */
    @ApiModelProperty("카드유효기간")
    private String expiry;


    /**
     * 카드 생년월일(사업자등록번호)
     */
    @ApiModelProperty("카드 생년월일(사업자등록번호)")
    private String birth;


    /**
     * 카드 비밀번호 앞 두자리 XX
     */
    @ApiModelProperty("카드 비밀번호 앞 두자리 XX")
    private String pwd2digit;


    /**
     * 카드(빌링키)와 1:1로 대응하는 값
     */
    @ApiModelProperty("카드(빌링키)와 1:1로 대응하는 값")
    private String customerUid;


    /**
     * 자동결제(1:자동결제안함, 2:첫결제신청, 3: 해제, 4:첫결제 이후 재결제, 6:강제해제)
     */
    @ApiModelProperty("자동결제(1:자동결제안함, 2:첫결제신청, 3: 해제, 4:첫결제 이후 재결제, 6:강제해제)")
    private Integer autoPay;


    /**
     * 서비스 결제 X 강제 해지시 결제 안한 금액
     */
    @ApiModelProperty("서비스 결제 X 강제 해지시 결제 안한 금액")
    private Integer stopServicePrice;


    /**
     * 자동결제 해지일시
     */
    @ApiModelProperty("자동결제 해지일시")
    private Date notAutoPayDate;


    /**
     * 회원권 시작일
     */
    @ApiModelProperty("회원권 시작일")
    private Date validStart;


    /**
     * 회원권 종료일
     */
    @ApiModelProperty("회원권 종료일")
    private Date validEnd;


    /**
     * 결제요청UID
     */
    @ApiModelProperty("결제요청UID")
    private String payRequestUid;


    /**
     * 사업자등록증사본
     */
    @ApiModelProperty("사업자등록증사본")
    private String fileGroupId;


    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    private Integer adminIdx;


    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    private Integer modifierIdx;


    /**
     * 수정자 이름
     */
    @ApiModelProperty("수정자 이름")
    private String modifierName;


    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    private Date modifyDate;


    /**
     * 암호화한 키
     */
    @ApiModelProperty("암호화한 키")
    private String encryptText;


    /**
     * 복호화에 사용할 데이터 키
     */
    @ApiModelProperty("복호화에 사용할 데이터 키")
    private String dataKey;


    /**
     * 누적 휴면회원 수
     */
    @ApiModelProperty("누적 휴면회원 수")
    private Integer dormantAccumulate;

}

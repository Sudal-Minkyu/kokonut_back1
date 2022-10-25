package com.app.kokonut.payment.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class PaymentQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * COMPANY IDX
     */
    @ApiModelProperty("COMPANY IDX")
    private Integer companyIdx;


    /**
     * 결제자 키(관리자)
     */
    @ApiModelProperty("결제자 키(관리자)")
    private Integer adminIdx;


    /**
     * 결제UID
     */
    @ApiModelProperty("결제UID")
    private String payRequestUid;


    /**
     * 상점거래ID
     */
    @ApiModelProperty("상점거래ID")
    private String merchantUid;


    /**
     * 결제번호
     */
    @ApiModelProperty("결제번호")
    private String impUid;


    /**
     * 고유거래번호
     */
    @ApiModelProperty("고유거래번호")
    private String pgTid;


    /**
     * 상품(PREMIUM, STANDARD, PREMIUM)
     */
    @ApiModelProperty("상품(PREMIUM, STANDARD, PREMIUM)")
    private String service;


    /**
     * 요금부과기간 시작일
     */
    @ApiModelProperty("요금부과기간 시작일")
    private Date validStart;


    /**
     * 요금부과기간 종료일
     */
    @ApiModelProperty("요금부과기간 종료일")
    private Date validEnd;


    /**
     * 기준 회원수
     */
    @ApiModelProperty("기준 회원수")
    private Integer userCount;


    /**
     * 결제금액
     */
    @ApiModelProperty("결제금액")
    private Integer amount;


    /**
     * 상태(0:결제오류,1:결제완료)
     */
    @ApiModelProperty("상태(0:결제오류,1:결제완료)")
    private Integer state;


    /**
     * 카드이름
     */
    @ApiModelProperty("카드이름")
    private String cardName;


    /**
     * 카드번호
     */
    @ApiModelProperty("카드번호")
    private String cardNumber;


    /**
     * 결제방법(AUTO_CARD:자동결제, FEE_CALCULATE:요금정산, FAIL : 결제실패)
     */
    @ApiModelProperty("결제방법(AUTO_CARD:자동결제, FEE_CALCULATE:요금정산, FAIL : 결제실패)")
    private String payMethod;


    /**
     * 거래전표 URL
     */
    @ApiModelProperty("거래전표 URL")
    private String receiptUrl;


    /**
     * 환불신청상태
     */
    @ApiModelProperty("환불신청상태")
    private String applyRefund;


    /**
     * 환불신청날짜
     */
    @ApiModelProperty("환불신청날짜")
    private Date refundApplyDate;


    /**
     * 환불상태
     */
    @ApiModelProperty("환불상태")
    private String refundState;


    /**
     * 환불사유
     */
    @ApiModelProperty("환불사유")
    private String refundReason;


    /**
     * 환불날짜
     */
    @ApiModelProperty("환불날짜")
    private Date refundDate;


    /**
     * 결제일시
     */
    @ApiModelProperty("결제일시")
    private Date regdate;

}

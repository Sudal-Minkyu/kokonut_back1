package com.app.kokonut.statisticsDaySystem.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "statistics_day_system")
public class StatisticsDaySystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * 날짜(일자로 기록)
     */
    @Column(name = "DATE")
    @ApiModelProperty("날짜(일자로 기록)")
    private Date date;

    /**
     * 신규회원
     */
    @ApiModelProperty("신규회원")
    @Column(name = "NEW_MEMBER")
    private Integer newMember;

    /**
     * 신규사업자회원
     */
    @ApiModelProperty("신규사업자회원")
    @Column(name = "NEW_MASTER_MEMBER")
    private Integer newMasterMember;

    /**
     * 신규개인회원
     */
    @ApiModelProperty("신규개인회원")
    @Column(name = "NEW_ADMIN_MEMBER")
    private Integer newAdminMember;

    /**
     * 휴면계정전환
     */
    @Column(name = "DORMANT")
    @ApiModelProperty("휴면계정전환")
    private Integer dormant;

    /**
     * 회원탈퇴,회원탈퇴해지(이탈총합은 더해서 표현)
     */
    @Column(name = "WITHDRAWAL")
    @ApiModelProperty("회원탈퇴,회원탈퇴해지(이탈총합은 더해서 표현)")
    private Integer withdrawal;

    /**
     * 서비스 BASIC
     */
    @Column(name = "BASIC")
    @ApiModelProperty("서비스 BASIC")
    private Integer basic;

    /**
     * 서비스 STANDARD
     */
    @Column(name = "STANDARD")
    @ApiModelProperty("서비스 STANDARD")
    private Integer standard;

    /**
     * 서비스 PREMIUM
     */
    @Column(name = "PREMIUM")
    @ApiModelProperty("서비스 PREMIUM")
    private Integer premium;

    /**
     * 자동결제해지
     */
    @ApiModelProperty("자동결제해지")
    @Column(name = "AUTO_CANCEL")
    private Integer autoCancel;

    /**
     * 회원탈퇴해지
     */
    @ApiModelProperty("회원탈퇴해지")
    @Column(name = "WITHDRAWAL_CANCEL")
    private Integer withdrawalCancel;

    /**
     * BASIC 결제금액
     */
    @Column(name = "BASIC_AMOUNT")
    @ApiModelProperty("BASIC 결제금액")
    private Integer basicAmount;

    /**
     * STANDARD 결제금액
     */
    @Column(name = "STANDARD_AMOUNT")
    @ApiModelProperty("STANDARD 결제금액")
    private Integer standardAmount;

    /**
     * PREMIUM 결제금액
     */
    @Column(name = "PREMIUM_AMOUNT")
    @ApiModelProperty("PREMIUM 결제금액")
    private Integer premiumAmount;

    /**
     * 개인정보열람이력
     */
    @ApiModelProperty("개인정보열람이력")
    @Column(name = "PERSONAL_HISTORY")
    private Integer personalHistory;

    /**
     * 관리자열람이력
     */
    @ApiModelProperty("관리자열람이력")
    @Column(name = "ADMIN_HISTORY")
    private Integer adminHistory;

}

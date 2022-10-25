package com.app.kokonut.statisticsDay.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "statistics_day")
public class StatisticsDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
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
     * 날짜(일자로 기록)
     */
    @Column(name = "DATE")
    @ApiModelProperty("날짜(일자로 기록)")
    private Date date;

    /**
     * 일평균 회원 수
     */
    @Column(name = "ALL_MEMBER")
    @ApiModelProperty("일평균 회원 수")
    private Integer allMember;

    /**
     * 개인회원(신규가입총합은 더해서 표현)
     */
    @Column(name = "NEW_MEMBER")
    @ApiModelProperty("개인회원(신규가입총합은 더해서 표현)")
    private Integer newMember;

    /**
     * 휴면계정전환
     */
    @Column(name = "DORMANT")
    @ApiModelProperty("휴면계정전환")
    private Integer dormant;

    /**
     * 회원탈퇴
     */
    @ApiModelProperty("회원탈퇴")
    @Column(name = "WITHDRAWAL")
    private Integer withdrawal;

    /**
     * 개인정보 열람 이력
     */
    @ApiModelProperty("개인정보 열람 이력")
    @Column(name = "PERSONAL_HISTORY")
    private Integer personalHistory;

    /**
     * 관리자 열람 이력
     */
    @ApiModelProperty("관리자 열람 이력")
    @Column(name = "ADMIN_HISTORY")
    private Integer adminHistory;

}

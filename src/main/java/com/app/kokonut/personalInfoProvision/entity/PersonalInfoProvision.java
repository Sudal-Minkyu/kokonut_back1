package com.app.kokonut.personalInfoProvision.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Woody
 * Date : 2022-11-01
 * Time :
 * Remark : personal_info_provision Table Entity
 */
@Data
@Entity
@Table(name = "personal_info_provision")
public class PersonalInfoProvision implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @Id
    @ApiModelProperty("키")
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * 기업 키
     */
    @ApiModelProperty("기업 키")
    @Column(name = "COMPANY_IDX", nullable = false)
    private Integer companyIdx;

    /**
     * 관리자 키 (=등록자)
     */
    @ApiModelProperty("관리자 키 (=등록자)")
    @Column(name = "ADMIN_IDX", nullable = false)
    private Integer adminIdx;

    /**
     * 관리번호
     */
    @ApiModelProperty("관리번호")
    @Column(name = "NUMBER", nullable = false)
    private String number;

    /**
     * 필요사유 (1: 서비스운영, 2: 이벤트/프로모션, 3: 제휴, 4: 광고/홍보)
     */
    @Column(name = "REASON", nullable = false)
    @ApiModelProperty("필요사유 (1: 서비스운영, 2: 이벤트/프로모션, 3: 제휴, 4: 광고/홍보)")
    private Integer reason;

    /**
     * 항목유형 (1: 회원정보 전체 항목, 2: 개인 식별 정보를 포함한 일부 항목, 3: 개인 식별 정보를 포함하지 않는 일부 항목)
     */
    @Column(name = "TYPE", nullable = false)
    @ApiModelProperty("항목유형 (1: 회원정보 전체 항목, 2: 개인 식별 정보를 포함한 일부 항목, 3: 개인 식별 정보를 포함하지 않는 일부 항목)")
    private Integer type;

    /**
     * 받는사람 유형 (1: 내부직원, 2: 제3자, 3: 본인, 4: 위수탁)
     */
    @Column(name = "RECIPIENT_TYPE", nullable = false)
    @ApiModelProperty("받는사람 유형 (1: 내부직원, 2: 제3자, 3: 본인, 4: 위수탁)")
    private Integer recipientType;

    /**
     * 정보제공 동의여부 (Y/N)
     */
    @ApiModelProperty("정보제공 동의여부 (Y/N)")
    @Column(name = "AGREE_YN", nullable = false)
    private String agreeYn;

    /**
     * 정보제공 동의유형 (1: 고정필드, 2: 별도수집) (AGREE_YN 이 'Y'인 경우에만 저장)
     */
    @Column(name = "AGREE_TYPE")
    @ApiModelProperty("정보제공 동의유형 (1: 고정필드, 2: 별도수집) (AGREE_YN 이 'Y'인 경우에만 저장)")
    private Integer agreeType;

    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 목적
     */
    @ApiModelProperty("목적")
    @Column(name = "PURPOSE", nullable = false)
    private String purpose;

    /**
     * 태그
     */
    @ApiModelProperty("태그")
    @Column(name = "TAG", nullable = false)
    private String tag;

    /**
     * 제공 시작일
     */
    @ApiModelProperty("제공 시작일")
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    /**
     * 제공 만료일 (=제공 시작일+제공 기간)
     */
    @ApiModelProperty("제공 만료일 (=제공 시작일+제공 기간)")
    @Column(name = "EXP_DATE", nullable = false)
    private Date expDate;

    /**
     * 제공 기간 (일)
     */
    @ApiModelProperty("제공 기간 (일)")
    @Column(name = "PERIOD", nullable = false)
    private Integer period;

    /**
     * 보유기간 (사용후 즉시 삭제: IMMEDIATELY, 한달: MONTH, 일년: YEAR)
     */
    @Column(name = "RETENTION_PERIOD", nullable = false)
    @ApiModelProperty("보유기간 (사용후 즉시 삭제: IMMEDIATELY, 한달: MONTH, 일년: YEAR)")
    private String retentionPeriod;

    /**
     * 제공 항목 (컬럼 목록, 구분자: ',')
     */
    @Column(name = "COLUMNS", nullable = false)
    @ApiModelProperty("제공 항목 (컬럼 목록, 구분자: ',')")
    private String columns;

    /**
     * 받는사람 이메일REASON
     */
    @ApiModelProperty("받는사람 이메일")
    @Column(name = "RECIPIENT_EMAIL", nullable = false)
    private String recipientEmail;

    /**
     * 제공 대상 (키 목록, 구분자: ',')
     */
    @Column(name = "TARGETS", nullable = false)
    @ApiModelProperty("제공 대상 (키 목록, 구분자: ',')")
    private String targets;

    /**
     * 제공 대상 상태 (전체: ALL, 선택완료: SELETED)
     */
    @Column(name = "TARGET_STATUS", nullable = false)
    @ApiModelProperty("제공 대상 상태 (전체: ALL, 선택완료: SELETED)")
    private String targetStatus;

}

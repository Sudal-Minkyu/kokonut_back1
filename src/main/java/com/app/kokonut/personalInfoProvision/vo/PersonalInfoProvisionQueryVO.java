package com.app.kokonut.personalInfoProvision.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class PersonalInfoProvisionQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 기업 키
     */
    @ApiModelProperty("기업 키")
    private Integer companyIdx;


    /**
     * 관리자 키 (=등록자)
     */
    @ApiModelProperty("관리자 키 (=등록자)")
    private Integer adminIdx;


    /**
     * 관리번호
     */
    @ApiModelProperty("관리번호")
    private String number;


    /**
     * 필요사유 (1: 서비스운영, 2: 이벤트/프로모션, 3: 제휴, 4: 광고/홍보)
     */
    @ApiModelProperty("필요사유 (1: 서비스운영, 2: 이벤트/프로모션, 3: 제휴, 4: 광고/홍보)")
    private Integer reason;


    /**
     * 항목유형 (1: 회원정보 전체 항목, 2: 개인 식별 정보를 포함한 일부 항목, 3: 개인 식별 정보를 포함하지 않는 일부 항목)
     */
    @ApiModelProperty("항목유형 (1: 회원정보 전체 항목, 2: 개인 식별 정보를 포함한 일부 항목, 3: 개인 식별 정보를 포함하지 않는 일부 항목)")
    private Integer type;


    /**
     * 받는사람 유형 (1: 내부직원, 2: 제3자, 3: 본인, 4: 위수탁)
     */
    @ApiModelProperty("받는사람 유형 (1: 내부직원, 2: 제3자, 3: 본인, 4: 위수탁)")
    private Integer recipientType;


    /**
     * 정보제공 동의여부 (Y/N)
     */
    @ApiModelProperty("정보제공 동의여부 (Y/N)")
    private String agreeYn;


    /**
     * 정보제공 동의유형 (1: 고정필드, 2: 별도수집) (AGREE_YN 이 'Y'인 경우에만 저장)
     */
    @ApiModelProperty("정보제공 동의유형 (1: 고정필드, 2: 별도수집) (AGREE_YN 이 'Y'인 경우에만 저장)")
    private Integer agreeType;


    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 목적
     */
    @ApiModelProperty("목적")
    private String purpose;


    /**
     * 태그
     */
    @ApiModelProperty("태그")
    private String tag;


    /**
     * 제공 시작일
     */
    @ApiModelProperty("제공 시작일")
    private Date startDate;


    /**
     * 제공 만료일 (=제공 시작일+제공 기간)
     */
    @ApiModelProperty("제공 만료일 (=제공 시작일+제공 기간)")
    private Date expDate;


    /**
     * 제공 기간 (일)
     */
    @ApiModelProperty("제공 기간 (일)")
    private Integer period;


    /**
     * 보유기간 (사용후 즉시 삭제: IMMEDIATELY, 한달: MONTH, 일년: YEAR)
     */
    @ApiModelProperty("보유기간 (사용후 즉시 삭제: IMMEDIATELY, 한달: MONTH, 일년: YEAR)")
    private String retentionPeriod;


    /**
     * 제공 항목 (컬럼 목록, 구분자: ',')
     */
    @ApiModelProperty("제공 항목 (컬럼 목록, 구분자: ',')")
    private String columns;


    /**
     * 받는사람 이메일
     */
    @ApiModelProperty("받는사람 이메일")
    private String recipientEmail;


    /**
     * 제공 대상 (키 목록, 구분자: ',')
     */
    @ApiModelProperty("제공 대상 (키 목록, 구분자: ',')")
    private String targets;


    /**
     * 제공 대상 상태 (전체: ALL, 선택완료: SELETED)
     */
    @ApiModelProperty("제공 대상 상태 (전체: ALL, 선택완료: SELETED)")
    private String targetStatus;

}

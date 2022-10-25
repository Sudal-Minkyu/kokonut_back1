package com.app.kokonut.totalDBDownload.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "total_db_download")
public class TotalDbDownload implements Serializable {

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
     * 요청자
     */
    @ApiModelProperty("요청자")
    @Column(name = "ADMIN_IDX")
    private Integer adminIdx;

    /**
     * 요청사유
     */
    @Column(name = "REASON")
    @ApiModelProperty("요청사유")
    private String reason;

    /**
     * 요청일자
     */
    @ApiModelProperty("요청일자")
    @Column(name = "APPLY_DATE")
    private Date applyDate;

    /**
     * 다운로드 링크
     */
    @Column(name = "LINK")
    @ApiModelProperty("다운로드 링크")
    private String link;

    /**
     * 상태(1:다운로드요청, 2:다운로드승인(다운로드대기), 3:다운로드완료, 4:반려)
     */
    @Column(name = "STATE")
    @ApiModelProperty("상태(1:다운로드요청, 2:다운로드승인(다운로드대기), 3:다운로드완료, 4:반려)")
    private Integer state;

    /**
     * 반려사유
     */
    @ApiModelProperty("반려사유")
    @Column(name = "RETURN_REASON")
    private String returnReason;

    /**
     * 횟수제한
     */
    @Column(name = "LIMIT")
    @ApiModelProperty("횟수제한")
    private Integer limit;

    /**
     * 기간제한 시작일자
     */
    @ApiModelProperty("기간제한 시작일자")
    @Column(name = "LIMIT_DATE_START")
    private Date limitDateStart;

    /**
     * 기간제한 종료일자
     */
    @ApiModelProperty("기간제한 종료일자")
    @Column(name = "LIMIT_DATE_END")
    private Date limitDateEnd;

    /**
     * 다운로드 일자
     */
    @ApiModelProperty("다운로드 일자")
    @Column(name = "DOWNLOAD_DATE")
    private Date downloadDate;

    /**
     * 다운로드정보 등록자
     */
    @Column(name = "REGISTER_IDX")
    @ApiModelProperty("다운로드정보 등록자")
    private Integer registerIdx;

    /**
     * 다운로드정보 등록자 이름
     */
    @Column(name = "REGISTER_NAME")
    @ApiModelProperty("다운로드정보 등록자 이름")
    private String registerName;

    /**
     * 다운로드정보 등록일시
     */
    @Column(name = "REGIST_DATE")
    @ApiModelProperty("다운로드정보 등록일시")
    private Date registDate;

    /**
     * 다운로드정보 수정자
     */
    @Column(name = "MODIFIER_IDX")
    @ApiModelProperty("다운로드정보 수정자")
    private Integer modifierIdx;

    /**
     * 다운로드정보 수정자 이름
     */
    @Column(name = "MODIFIER_NAME")
    @ApiModelProperty("다운로드정보 수정자 이름")
    private String modifierName;

    /**
     * 다운로드정보 수정일시
     */
    @Column(name = "MODIFY_DATE")
    @ApiModelProperty("다운로드정보 수정일시")
    private Date modifyDate;

    /**
     * IP주소(다운로드정보에 표현)
     */
    @Column(name = "IP_ADDR")
    @ApiModelProperty("IP주소(다운로드정보에 표현)")
    private String ipAddr;

    /**
     * 요청일시
     */
    @Column(name = "REGDATE")
    @ApiModelProperty("요청일시")
    private Date regdate;

}

package com.app.kokonut.refactor.totalDBDownload.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class TotalDbDownloadQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 요청자
     */
    @ApiModelProperty("요청자")
    private Integer adminIdx;


    /**
     * 요청사유
     */
    @ApiModelProperty("요청사유")
    private String reason;


    /**
     * 요청일자
     */
    @ApiModelProperty("요청일자")
    private Date applyDate;


    /**
     * 다운로드 링크
     */
    @ApiModelProperty("다운로드 링크")
    private String link;


    /**
     * 상태(1:다운로드요청, 2:다운로드승인(다운로드대기), 3:다운로드완료, 4:반려)
     */
    @ApiModelProperty("상태(1:다운로드요청, 2:다운로드승인(다운로드대기), 3:다운로드완료, 4:반려)")
    private Integer state;


    /**
     * 반려사유
     */
    @ApiModelProperty("반려사유")
    private String returnReason;


    /**
     * 횟수제한
     */
    @ApiModelProperty("횟수제한")
    private Integer limit;


    /**
     * 기간제한 시작일자
     */
    @ApiModelProperty("기간제한 시작일자")
    private Date limitDateStart;


    /**
     * 기간제한 종료일자
     */
    @ApiModelProperty("기간제한 종료일자")
    private Date limitDateEnd;


    /**
     * 다운로드 일자
     */
    @ApiModelProperty("다운로드 일자")
    private Date downloadDate;


    /**
     * 다운로드정보 등록자
     */
    @ApiModelProperty("다운로드정보 등록자")
    private Integer registerIdx;


    /**
     * 다운로드정보 등록자 이름
     */
    @ApiModelProperty("다운로드정보 등록자 이름")
    private String registerName;


    /**
     * 다운로드정보 등록일시
     */
    @ApiModelProperty("다운로드정보 등록일시")
    private Date registDate;


    /**
     * 다운로드정보 수정자
     */
    @ApiModelProperty("다운로드정보 수정자")
    private Integer modifierIdx;


    /**
     * 다운로드정보 수정자 이름
     */
    @ApiModelProperty("다운로드정보 수정자 이름")
    private String modifierName;


    /**
     * 다운로드정보 수정일시
     */
    @ApiModelProperty("다운로드정보 수정일시")
    private Date modifyDate;


    /**
     * IP주소(다운로드정보에 표현)
     */
    @ApiModelProperty("IP주소(다운로드정보에 표현)")
    private String ipAddr;


    /**
     * 요청일시
     */
    @ApiModelProperty("요청일시")
    private Date regdate;

}

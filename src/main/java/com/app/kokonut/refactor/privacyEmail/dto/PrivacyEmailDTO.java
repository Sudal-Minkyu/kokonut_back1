package com.app.kokonut.refactor.privacyEmail.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("")
public class PrivacyEmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 발신자 이메일
     */
    @ApiModelProperty("발신자 이메일")
    private String senderEmail;


    /**
     * 제목
     */
    @ApiModelProperty("제목")
    private String title;


    /**
     * 내용
     */
    @ApiModelProperty("내용")
    private String contents;


    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 기업 키(기업별 이메일인 경우)
     */
    @ApiModelProperty("기업 키(기업별 이메일인 경우)")
    private Integer companyIdx;

}

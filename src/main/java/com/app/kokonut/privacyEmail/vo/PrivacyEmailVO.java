package com.app.kokonut.privacyEmail.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class PrivacyEmailVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 발신자 이메일
     */
    @NotNull(message = "senderEmail can not null")
    @ApiModelProperty("발신자 이메일")
    private String senderEmail;


    /**
     * 제목
     */
    @NotNull(message = "title can not null")
    @ApiModelProperty("제목")
    private String title;


    /**
     * 내용
     */
    @NotNull(message = "contents can not null")
    @ApiModelProperty("내용")
    private String contents;


    /**
     * 등록일
     */
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 기업 키(기업별 이메일인 경우)
     */
    @ApiModelProperty("기업 키(기업별 이메일인 경우)")
    private Integer companyIdx;

}

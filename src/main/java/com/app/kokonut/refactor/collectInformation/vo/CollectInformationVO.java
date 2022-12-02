package com.app.kokonut.refactor.collectInformation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class CollectInformationVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 주키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("주키")
    private Integer idx;


    /**
     * 회사 IDX
     */
    @ApiModelProperty("회사 IDX")
    private Integer companyIdx;


    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    private Integer adminIdx;


    /**
     * 제목
     */
    @ApiModelProperty("제목")
    private String title;


    /**
     * 내용
     */
    @ApiModelProperty("내용")
    private String content;


    /**
     * 등록자 이름
     */
    @ApiModelProperty("등록자 이름")
    private String registerName;


    /**
     * 등록일
     */
    @NotNull(message = "regdate can not null")
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
     * 수정자
     */
    @NotNull(message = "modifyDate can not null")
    @ApiModelProperty("수정자")
    private Date modifyDate;

}

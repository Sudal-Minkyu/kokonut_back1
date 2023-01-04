package com.app.kokonut.email.emailHistory.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("")
public class EmailHistoryDto implements Serializable {

    /**
     * 보내는 사람 이메일
     */
    @ApiModelProperty("보내는 사람 이메일")
    private String from;


    /**
     * 보내는 사람 이름
     */
    @ApiModelProperty("보내는 사람 이름")
    private String fromName;


    /**
     * 받는 사람 이메일
     */
    @ApiModelProperty("받는 사람 이메일")
    private String to;


    /**
     * 받는 사람 이름
     */
    @ApiModelProperty("받는 사람 이름")
    private String toName;


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

}

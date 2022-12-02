package com.app.kokonut.refactor.emailHistory.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("")
public class EmailHistoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


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


    /**
     * 발송일시
     */
    @ApiModelProperty("발송일시")
    private Date regdate;

}

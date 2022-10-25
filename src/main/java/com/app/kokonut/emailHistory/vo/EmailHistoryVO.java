package com.app.kokonut.emailHistory.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class EmailHistoryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 보내는 사람 이메일
     */
    @NotNull(message = "from can not null")
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
    @NotNull(message = "to can not null")
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
     * 발송일시
     */
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("발송일시")
    private Date regdate;

}

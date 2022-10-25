package com.app.kokonut.email.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class EmailVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 보내는 관리자 키(시스템 관리자 고정)
     */
    @NotNull(message = "senderAdminIdx can not null")
    @ApiModelProperty("보내는 관리자 키(시스템 관리자 고정)")
    private Integer senderAdminIdx;


    /**
     * 받는사람 타입(I:개별,G:그룹)
     */
    @NotNull(message = "receiverType can not null")
    @ApiModelProperty("받는사람 타입(I:개별,G:그룹)")
    private String receiverType;


    /**
     * 받는 관리자 키(문자열, 구분자: ',')
     */
    @ApiModelProperty("받는 관리자 키(문자열, 구분자: ',')")
    private String receiverAdminIdxList;


    /**
     * 받는 그룹 키
     */
    @ApiModelProperty("받는 그룹 키")
    private Integer emailGroupIdx;


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

}

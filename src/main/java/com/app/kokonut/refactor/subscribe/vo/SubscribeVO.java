package com.app.kokonut.refactor.subscribe.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class SubscribeVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 주키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("주키")
    private Integer idx;


    /**
     * 서비스 이름
     */
    @ApiModelProperty("서비스 이름")
    private String service;


    /**
     * 서비스 금액
     */
    @ApiModelProperty("서비스 금액")
    private Integer price;


    /**
     * 평균 회원 1명당 금액
     */
    @ApiModelProperty("평균 회원 1명당 금액")
    private Integer perPrice;


    /**
     * 등록일
     */
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 수정일
     */
    @NotNull(message = "modifyDate can not null")
    @ApiModelProperty("수정일")
    private Date modifyDate;

}

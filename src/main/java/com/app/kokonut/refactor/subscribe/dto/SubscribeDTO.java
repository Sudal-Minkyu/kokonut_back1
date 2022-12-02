package com.app.kokonut.refactor.subscribe.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("")
public class SubscribeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 주키
     */
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
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 수정일
     */
    @ApiModelProperty("수정일")
    private Date modifyDate;

}

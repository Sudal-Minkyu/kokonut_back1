package com.app.kokonut.refactor.awsKmsHistory.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class AwsKmsHistoryQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 호출 날짜
     */
    @ApiModelProperty("호출 날짜")
    private Date regdate;


    /**
     * 호출 타입
     */
    @ApiModelProperty("호출 타입")
    private String type;

}

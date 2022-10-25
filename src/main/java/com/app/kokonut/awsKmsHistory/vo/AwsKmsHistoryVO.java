package com.app.kokonut.awsKmsHistory.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class AwsKmsHistoryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 호출 날짜
     */
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("호출 날짜")
    private Date regdate;


    /**
     * 호출 타입
     */
    @ApiModelProperty("호출 타입")
    private String type;

}

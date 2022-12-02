package com.app.kokonut.refactor.shedlock.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query 스케줄잠금")
public class ShedlockQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 스케줄잠금이름
     */
    @ApiModelProperty("스케줄잠금이름")
    private String name;


    /**
     * 잠금기간
     */
    @ApiModelProperty("잠금기간")
    private Date lockUntil;


    /**
     * 잠금일시
     */
    @ApiModelProperty("잠금일시")
    private Date lockedAt;


    /**
     * 잠금신청자
     */
    @ApiModelProperty("잠금신청자")
    private String lockedBy;

}

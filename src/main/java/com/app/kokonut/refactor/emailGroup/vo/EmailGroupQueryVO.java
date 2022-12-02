package com.app.kokonut.refactor.emailGroup.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class EmailGroupQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 관리자 키(문자열, 구분자: ',')
     */
    @ApiModelProperty("관리자 키(문자열, 구분자: ',')")
    private String adminIdxList;


    /**
     * 그룹명
     */
    @ApiModelProperty("그룹명")
    private String name;


    /**
     * 그룹설명
     */
    @ApiModelProperty("그룹설명")
    private String desc;


    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 사용여부
     */
    @ApiModelProperty("사용여부")
    private String useYn;

}

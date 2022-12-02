package com.app.kokonut.refactor.emailGroup.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class EmailGroupVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 관리자 키(문자열, 구분자: ',')
     */
    @NotNull(message = "adminIdxList can not null")
    @ApiModelProperty("관리자 키(문자열, 구분자: ',')")
    private String adminIdxList;


    /**
     * 그룹명
     */
    @NotNull(message = "name can not null")
    @ApiModelProperty("그룹명")
    private String name;


    /**
     * 그룹설명
     */
    @NotNull(message = "desc can not null")
    @ApiModelProperty("그룹설명")
    private String desc;


    /**
     * 등록일
     */
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 사용여부
     */
    @NotNull(message = "useYn can not null")
    @ApiModelProperty("사용여부")
    private String useYn;

}

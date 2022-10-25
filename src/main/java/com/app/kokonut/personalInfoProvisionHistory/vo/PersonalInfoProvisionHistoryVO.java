package com.app.kokonut.personalInfoProvisionHistory.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class PersonalInfoProvisionHistoryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * personal_info_provision 고유번호
     */
    @NotNull(message = "number can not null")
    @ApiModelProperty("personal_info_provision 고유번호")
    private String number;


    /**
     * 등록일
     */
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 회사 키
     */
    @NotNull(message = "companyIdx can not null")
    @ApiModelProperty("회사 키")
    private Integer companyIdx;


    /**
     * 관리자 키 (=수정자)
     */
    @ApiModelProperty("관리자 키 (=수정자)")
    private Integer adminIdx;

}

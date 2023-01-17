package com.app.kokonut.personalInfoProvision.personalInfoProvisionHistory.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("")
public class PersonalInfoProvisionHistoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * personal_info_provision 고유번호
     */
    @ApiModelProperty("personal_info_provision 고유번호")
    private String number;


    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 회사 키
     */
    @ApiModelProperty("회사 키")
    private Integer companyIdx;


    /**
     * 관리자 키 (=수정자)
     */
    @ApiModelProperty("관리자 키 (=수정자)")
    private Integer adminIdx;

}

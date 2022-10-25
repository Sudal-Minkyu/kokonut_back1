package com.app.kokonut.personalInfoProvisionAgree.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;


@Data
@ApiModel("Save ")
public class PersonalInfoProvisionAgreeVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * personal_info_provision 관리번호
     */
    @NotNull(message = "number can not null")
    @ApiModelProperty("personal_info_provision 관리번호")
    private String number;


    /**
     * 동의 일
     */
    @ApiModelProperty("동의 일")
    private Date agreeDate;


    /**
     * 동의 시간
     */
    @ApiModelProperty("동의 시간")
    private Time agreeTime;


    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    private java.util.Date regdate;


    /**
     * 대상 아이디
     */
    @NotNull(message = "id can not null")
    @ApiModelProperty("대상 아이디")
    private String id;


    /**
     * 파일그룹 아이디
     */
    @ApiModelProperty("파일그룹 아이디")
    private String fileGroupId;


    /**
     * 주의사항 동의여부 (Y/N)
     */
    @NotNull(message = "agreeYn can not null")
    @ApiModelProperty("주의사항 동의여부 (Y/N)")
    private String agreeYn;

}

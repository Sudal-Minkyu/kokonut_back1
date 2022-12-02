package com.app.kokonut.refactor.personalInfoDownloadHistory.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class PersonalInfoDownloadHistoryQueryVO implements Serializable {
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
     * 보유기간 만료일
     */
    @ApiModelProperty("보유기간 만료일")
    private Date retentionDate;


    /**
     * 이메일
     */
    @ApiModelProperty("이메일")
    private String email;


    /**
     * 파일명
     */
    @ApiModelProperty("파일명")
    private String fileName;


    /**
     * 주의사항 동의여부 (Y/N)
     */
    @ApiModelProperty("주의사항 동의여부 (Y/N)")
    private String agreeYn;


    /**
     * 정보제공 파기 파일그룹 아이디
     */
    @ApiModelProperty("정보제공 파기 파일그룹 아이디")
    private String destructionFileGroupId;


    /**
     * 정보제공 파기 주의사항 동의여부 (Y/N)
     */
    @ApiModelProperty("정보제공 파기 주의사항 동의여부 (Y/N)")
    private String destructionAgreeYn;


    /**
     * 정보제공 파기 최근 등록일
     */
    @ApiModelProperty("정보제공 파기 최근 등록일")
    private Date destructionDate;


    /**
     * 정보제공 파기 등록자
     */
    @ApiModelProperty("정보제공 파기 등록자")
    private String destrunctionRegisterName;

}

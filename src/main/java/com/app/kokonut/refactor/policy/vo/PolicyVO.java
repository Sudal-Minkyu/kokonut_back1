package com.app.kokonut.refactor.policy.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class PolicyVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 정책 등록자
     */
    @ApiModelProperty("정책 등록자")
    private Long adminId;


    /**
     * 정책(1:서비스이용약관,2:개인정보취급방침)
     */
    @ApiModelProperty("정책(1:서비스이용약관,2:개인정보취급방침)")
    private Integer type;


    /**
     * 시행일자
     */
    @ApiModelProperty("시행일자")
    private Date effectiveDate;


    /**
     * HTML코드(기획상에는 파일로 되어있어 FILE_GROUP_ID로 대체될 수 있다.)
     */
    @ApiModelProperty("HTML코드(기획상에는 파일로 되어있어 FILE_GROUP_ID로 대체될 수 있다.)")
    private String html;


    /**
     * 작성정보 작성자
     */
    @ApiModelProperty("작성정보 작성자")
    private String registerName;


    /**
     * 게시일시
     */
    @NotNull(message = "registDate can not null")
    @ApiModelProperty("게시일시")
    private Date registDate;


    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    private Date regdate;


    /**
     * 작성정보 수정자
     */
    @ApiModelProperty("작성정보 수정자")
    private Integer modifierIdx;


    /**
     * 작성정보 수정자 이름
     */
    @ApiModelProperty("작성정보 수정자 이름")
    private String modifierName;


    /**
     * 작성정보 수정일시
     */
    @ApiModelProperty("작성정보 수정일시")
    private Date modifyDate;

}

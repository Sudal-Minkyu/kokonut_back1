package com.app.kokonut.refactor.policy.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("")
public class PolicyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 정책 등록자
     */
    @ApiModelProperty("정책 등록자")
    private Integer adminIdx;


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

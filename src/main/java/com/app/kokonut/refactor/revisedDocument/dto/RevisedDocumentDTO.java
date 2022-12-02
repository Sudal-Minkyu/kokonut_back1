package com.app.kokonut.refactor.revisedDocument.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("")
public class RevisedDocumentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 회사(Company) 키
     */
    @ApiModelProperty("회사(Company) 키")
    private Integer companyIdx;


    /**
     * 시행시작일자
     */
    @ApiModelProperty("시행시작일자")
    private Date enforceStartDate;


    /**
     * 시행종료일자
     */
    @ApiModelProperty("시행종료일자")
    private Date enforceEndDate;


    /**
     * 파일그룹아이디
     */
    @ApiModelProperty("파일그룹아이디")
    private String fileGroupId;


    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    private Integer adminIdx;


    /**
     * 등록자이름
     */
    @ApiModelProperty("등록자이름")
    private String registerName;


    /**
     * 등록일자
     */
    @ApiModelProperty("등록일자")
    private Date regdate;

}

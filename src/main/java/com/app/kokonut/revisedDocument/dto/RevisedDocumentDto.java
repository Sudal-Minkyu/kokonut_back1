package com.app.kokonut.revisedDocument.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Joy
 * Date : 2023-01-94
 * Time :
 * Remark : 기본 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevisedDocumentDto implements Serializable {

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("회사(Company) 키")
    private Integer companyIdx;

    @ApiModelProperty("시행시작일자")
    private LocalDateTime enforceStartDate;

    @ApiModelProperty("시행종료일자")
    private LocalDateTime enforceEndDate;

    @ApiModelProperty("파일그룹아이디")
    private String fileGroupId;

    @ApiModelProperty("등록자")
    private Integer adminIdx;

    @ApiModelProperty("등록자이름")
    private String registerName;

    @ApiModelProperty("등록일자")
    private LocalDateTime regdate;

}

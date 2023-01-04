package com.app.kokonut.revisedDocument.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "revised_document")
public class RevisedDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("키")
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "COMPANY_IDX")
    @ApiModelProperty("회사(Company) 키")
    private Integer companyIdx;

    @ApiModelProperty("시행시작일자")
    @Column(name = "ENFORCE_START_DATE")
    private LocalDateTime enforceStartDate;

    @ApiModelProperty("시행종료일자")
    @Column(name = "ENFORCE_END_DATE")
    private LocalDateTime enforceEndDate;

    @ApiModelProperty("파일그룹아이디")
    @Column(name = "FILE_GROUP_ID")
    private String fileGroupId;

    @ApiModelProperty("등록자")
    @Column(name = "ADMIN_IDX")
    private Integer adminIdx;

    @ApiModelProperty("등록자이름")
    @Column(name = "REGISTER_NAME")
    private String registerName;

    @ApiModelProperty("등록일자")
    @Column(name = "REGDATE", nullable = false)
    private LocalDateTime regdate;

}

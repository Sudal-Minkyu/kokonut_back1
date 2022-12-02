package com.app.kokonut.refactor.revisedDocument.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "revised_document")
public class RevisedDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @Id
    @ApiModelProperty("키")
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * 회사(Company) 키
     */
    @Column(name = "COMPANY_IDX")
    @ApiModelProperty("회사(Company) 키")
    private Integer companyIdx;

    /**
     * 시행시작일자
     */
    @ApiModelProperty("시행시작일자")
    @Column(name = "ENFORCE_START_DATE")
    private Date enforceStartDate;

    /**
     * 시행종료일자
     */
    @ApiModelProperty("시행종료일자")
    @Column(name = "ENFORCE_END_DATE")
    private Date enforceEndDate;

    /**
     * 파일그룹아이디
     */
    @ApiModelProperty("파일그룹아이디")
    @Column(name = "FILE_GROUP_ID")
    private String fileGroupId;

    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    @Column(name = "ADMIN_IDX")
    private Integer adminIdx;

    /**
     * 등록자이름
     */
    @ApiModelProperty("등록자이름")
    @Column(name = "REGISTER_NAME")
    private String registerName;

    /**
     * 등록일자
     */
    @ApiModelProperty("등록일자")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

}

package com.app.kokonut.refactor.collectInformation.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "collect_information")
public class CollectInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 주키
     */
    @Id
    @ApiModelProperty("주키")
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * 회사 IDX
     */
    @ApiModelProperty("회사 IDX")
    @Column(name = "COMPANY_IDX")
    private Integer companyIdx;

    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    @Column(name = "ADMIN_IDX")
    private Integer adminIdx;

    /**
     * 제목
     */
    @Column(name = "TITLE")
    @ApiModelProperty("제목")
    private String title;

    /**
     * 내용
     */
    @ApiModelProperty("내용")
    @Column(name = "CONTENT")
    private String content;

    /**
     * 등록자 이름
     */
    @ApiModelProperty("등록자 이름")
    @Column(name = "REGISTER_NAME")
    private String registerName;

    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    @Column(name = "MODIFIER_IDX")
    private Integer modifierIdx;

    /**
     * 수정자 이름
     */
    @ApiModelProperty("수정자 이름")
    @Column(name = "MODIFIER_NAME")
    private String modifierName;

    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    @Column(name = "MODIFY_DATE", nullable = false)
    private Date modifyDate;

}

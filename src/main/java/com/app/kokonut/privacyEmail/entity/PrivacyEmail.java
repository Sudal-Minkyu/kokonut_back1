package com.app.kokonut.privacyEmail.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "privacy_email")
public class PrivacyEmail implements Serializable {

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
     * 발신자 이메일
     */
    @ApiModelProperty("발신자 이메일")
    @Column(name = "SENDER_EMAIL", nullable = false)
    private String senderEmail;

    /**
     * 제목
     */
    @ApiModelProperty("제목")
    @Column(name = "TITLE", nullable = false)
    private String title;

    /**
     * 내용
     */
    @ApiModelProperty("내용")
    @Column(name = "CONTENTS", nullable = false)
    private String contents;

    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 기업 키(기업별 이메일인 경우)
     */
    @Column(name = "COMPANY_IDX")
    @ApiModelProperty("기업 키(기업별 이메일인 경우)")
    private Integer companyIdx;

}

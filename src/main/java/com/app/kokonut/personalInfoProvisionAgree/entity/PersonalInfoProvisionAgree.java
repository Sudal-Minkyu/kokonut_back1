package com.app.kokonut.personalInfoProvisionAgree.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Data
@Entity
@Table(name = "personal_info_provision_agree")
public class PersonalInfoProvisionAgree implements Serializable {

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
     * personal_info_provision 관리번호
     */
    @Column(name = "NUMBER", nullable = false)
    @ApiModelProperty("personal_info_provision 관리번호")
    private String number;

    /**
     * 동의 일
     */
    @ApiModelProperty("동의 일")
    @Column(name = "AGREE_DATE")
    private Date agreeDate;

    /**
     * 동의 시간
     */
    @ApiModelProperty("동의 시간")
    @Column(name = "AGREE_TIME")
    private Time agreeTime;

    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    @Column(name = "REGDATE")
    private Date regdate;

    /**
     * 대상 아이디
     */
    @ApiModelProperty("대상 아이디")
    @Column(name = "ID", nullable = false)
    private String id;

    /**
     * 파일그룹 아이디
     */
    @ApiModelProperty("파일그룹 아이디")
    @Column(name = "FILE_GROUP_ID")
    private String fileGroupId;

    /**
     * 주의사항 동의여부 (Y/N)
     */
    @ApiModelProperty("주의사항 동의여부 (Y/N)")
    @Column(name = "AGREE_YN", nullable = false)
    private String agreeYn;

}

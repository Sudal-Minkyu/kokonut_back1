package com.app.kokonut.personalInfoProvisionHistory.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "personal_info_provision_history")
public class PersonalInfoProvisionHistory implements Serializable {

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
     * personal_info_provision 고유번호
     */
    @Column(name = "NUMBER", nullable = false)
    @ApiModelProperty("personal_info_provision 고유번호")
    private String number;

    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 회사 키
     */
    @ApiModelProperty("회사 키")
    @Column(name = "COMPANY_IDX", nullable = false)
    private Integer companyIdx;

    /**
     * 관리자 키 (=수정자)
     */
    @Column(name = "ADMIN_IDX")
    @ApiModelProperty("관리자 키 (=수정자)")
    private Integer adminIdx;

}

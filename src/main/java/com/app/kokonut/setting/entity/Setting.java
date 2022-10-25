package com.app.kokonut.setting.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "setting")
public class Setting implements Serializable {

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
     * 해외로그인차단(0:차단안함,1:차단)
     */
    @Column(name = "OVERSEAS_BLOCK")
    @ApiModelProperty("해외로그인차단(0:차단안함,1:차단)")
    private Integer overseasBlock;

/**
 * 휴면회원 전환 시(0:다른DB로 정보이관
 , 1:이관 없이 회원정보 삭제)
 */
    @Column(name = "DORMANT_ACCOUNT")
    @ApiModelProperty("휴면회원 전환 시(0:다른DB로 정보이관
            , 1:이관 없이 회원정보 삭제)")
    private Integer dormantAccount;

    /**
     * 등록일시
     */
    @Column(name = "REGDATE")
    @ApiModelProperty("등록일시")
    private Date regdate;

    /**
     * 수정일시
     */
    @ApiModelProperty("수정일시")
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

}

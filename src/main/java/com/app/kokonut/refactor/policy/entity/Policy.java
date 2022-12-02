package com.app.kokonut.refactor.policy.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "policy")
public class Policy implements Serializable {

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
     * 정책 등록자
     */
    @Column(name = "ADMIN_IDX")
    @ApiModelProperty("정책 등록자")
    private Integer adminIdx;

    /**
     * 정책(1:서비스이용약관,2:개인정보취급방침)
     */
    @Column(name = "TYPE")
    @ApiModelProperty("정책(1:서비스이용약관,2:개인정보취급방침)")
    private Integer type;

    /**
     * 시행일자
     */
    @ApiModelProperty("시행일자")
    @Column(name = "EFFECTIVE_DATE")
    private Date effectiveDate;

    /**
     * HTML코드(기획상에는 파일로 되어있어 FILE_GROUP_ID로 대체될 수 있다.)
     */
    @Column(name = "HTML")
    @ApiModelProperty("HTML코드(기획상에는 파일로 되어있어 FILE_GROUP_ID로 대체될 수 있다.)")
    private String html;

    /**
     * 작성정보 작성자
     */
    @ApiModelProperty("작성정보 작성자")
    @Column(name = "REGISTER_NAME")
    private String registerName;

    /**
     * 게시일시
     */
    @ApiModelProperty("게시일시")
    @Column(name = "REGIST_DATE", nullable = false)
    private Date registDate;

    /**
     * 등록일시
     */
    @Column(name = "REGDATE")
    @ApiModelProperty("등록일시")
    private Date regdate;

    /**
     * 작성정보 수정자
     */
    @ApiModelProperty("작성정보 수정자")
    @Column(name = "MODIFIER_IDX")
    private Integer modifierIdx;

    /**
     * 작성정보 수정자 이름
     */
    @Column(name = "MODIFIER_NAME")
    @ApiModelProperty("작성정보 수정자 이름")
    private String modifierName;

    /**
     * 작성정보 수정일시
     */
    @Column(name = "MODIFY_DATE")
    @ApiModelProperty("작성정보 수정일시")
    private Date modifyDate;

}

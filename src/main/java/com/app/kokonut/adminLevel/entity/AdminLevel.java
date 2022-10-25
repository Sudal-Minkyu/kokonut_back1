package com.app.kokonut.adminLevel.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "admin_level")
public class AdminLevel implements Serializable {

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
     * 관리자 등급
     */
    @Column(name = "LEVEL")
    @ApiModelProperty("관리자 등급")
    private String level;

    /**
     * API 메뉴얼 관리(0:사용안함,1:사용)
     */
    @Column(name = "MENU_1")
    @ApiModelProperty("API 메뉴얼 관리(0:사용안함,1:사용)")
    private Integer menu1 = 0;

    /**
     * 회원 관리(0:사용안함,1:사용)
     */
    @Column(name = "MENU_2")
    @ApiModelProperty("회원 관리(0:사용안함,1:사용)")
    private Integer menu2 = 0;

    /**
     * 관리자 관리(0:사용안함,1:사용)
     */
    @Column(name = "MENU_3")
    @ApiModelProperty("관리자 관리(0:사용안함,1:사용)")
    private Integer menu3 = 0;

    /**
     * 메일 발송 관리(0:사용안함,1:사용)
     */
    @Column(name = "MENU_4")
    @ApiModelProperty("메일 발송 관리(0:사용안함,1:사용)")
    private Integer menu4 = 0;

    /**
     * 카카오톡 메세지 관리(0:사용안함,1:사용)
     */
    @Column(name = "MENU_5")
    @ApiModelProperty("카카오톡 메세지 관리(0:사용안함,1:사용)")
    private Integer menu5 = 0;

    /**
     * 개인정보처리방침 관리(0:사용안함,1:사용)
     */
    @Column(name = "MENU_6")
    @ApiModelProperty("개인정보처리방침 관리(0:사용안함,1:사용)")
    private Integer menu6 = 0;

    /**
     * 설정(0:사용안함,1:사용)
     */
    @Column(name = "MENU_7")
    @ApiModelProperty("설정(0:사용안함,1:사용)")
    private Integer menu7 = 0;

    /**
     * 회원정보제공(0:사용안함,1:사용)
     */
    @Column(name = "MENU_8")
    @ApiModelProperty("회원정보제공(0:사용안함,1:사용)")
    private Integer menu8 = 0;

    /**
     * 사용안함(0:사용안함,1:사용)
     */
    @Column(name = "MENU_9")
    @ApiModelProperty("사용안함(0:사용안함,1:사용)")
    private Integer menu9 = 0;

    /**
     * 사용안함(0:사용안함,1:사용)
     */
    @Column(name = "MENU_10")
    @ApiModelProperty("사용안함(0:사용안함,1:사용)")
    private Integer menu10 = 0;

    /**
     * 사용안함(0:사용안함,1:사용)
     */
    @Column(name = "MENU_11")
    @ApiModelProperty("사용안함(0:사용안함,1:사용)")
    private Integer menu11 = 0;

    /**
     * 사용안함(0:사용안함,1:사용)
     */
    @Column(name = "MENU_12")
    @ApiModelProperty("사용안함(0:사용안함,1:사용)")
    private Integer menu12 = 0;

    /**
     * BASIC등급제한(사용안함)
     */
    @Column(name = "BASIC_LIMIT")
    @ApiModelProperty("BASIC등급제한(사용안함)")
    private Integer basicLimit = 0;

    /**
     * STANDARD등급제한(사용안함)
     */
    @Column(name = "STANDARD_LIMIT")
    @ApiModelProperty("STANDARD등급제한(사용안함)")
    private Integer standardLimit = 0;

    /**
     * PREMIUM등급제한(사용안함)
     */
    @Column(name = "PREMIUM_LIMIT")
    @ApiModelProperty("PREMIUM등급제한(사용안함)")
    private Integer premiumLimit = 0;

    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 수정일시
     */
    @ApiModelProperty("수정일시")
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

}

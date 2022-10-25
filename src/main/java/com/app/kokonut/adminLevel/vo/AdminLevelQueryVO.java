package com.app.kokonut.adminLevel.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class AdminLevelQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 관리자 등급
     */
    @ApiModelProperty("관리자 등급")
    private String level;


    /**
     * API 메뉴얼 관리(0:사용안함,1:사용)
     */
    @ApiModelProperty("API 메뉴얼 관리(0:사용안함,1:사용)")
    private Integer menu1;


    /**
     * 회원 관리(0:사용안함,1:사용)
     */
    @ApiModelProperty("회원 관리(0:사용안함,1:사용)")
    private Integer menu2;


    /**
     * 관리자 관리(0:사용안함,1:사용)
     */
    @ApiModelProperty("관리자 관리(0:사용안함,1:사용)")
    private Integer menu3;


    /**
     * 메일 발송 관리(0:사용안함,1:사용)
     */
    @ApiModelProperty("메일 발송 관리(0:사용안함,1:사용)")
    private Integer menu4;


    /**
     * 카카오톡 메세지 관리(0:사용안함,1:사용)
     */
    @ApiModelProperty("카카오톡 메세지 관리(0:사용안함,1:사용)")
    private Integer menu5;


    /**
     * 개인정보처리방침 관리(0:사용안함,1:사용)
     */
    @ApiModelProperty("개인정보처리방침 관리(0:사용안함,1:사용)")
    private Integer menu6;


    /**
     * 설정(0:사용안함,1:사용)
     */
    @ApiModelProperty("설정(0:사용안함,1:사용)")
    private Integer menu7;


    /**
     * 회원정보제공(0:사용안함,1:사용)
     */
    @ApiModelProperty("회원정보제공(0:사용안함,1:사용)")
    private Integer menu8;


    /**
     * 사용안함(0:사용안함,1:사용)
     */
    @ApiModelProperty("사용안함(0:사용안함,1:사용)")
    private Integer menu9;


    /**
     * 사용안함(0:사용안함,1:사용)
     */
    @ApiModelProperty("사용안함(0:사용안함,1:사용)")
    private Integer menu10;


    /**
     * 사용안함(0:사용안함,1:사용)
     */
    @ApiModelProperty("사용안함(0:사용안함,1:사용)")
    private Integer menu11;


    /**
     * 사용안함(0:사용안함,1:사용)
     */
    @ApiModelProperty("사용안함(0:사용안함,1:사용)")
    private Integer menu12;


    /**
     * BASIC등급제한(사용안함)
     */
    @ApiModelProperty("BASIC등급제한(사용안함)")
    private Integer basicLimit;


    /**
     * STANDARD등급제한(사용안함)
     */
    @ApiModelProperty("STANDARD등급제한(사용안함)")
    private Integer standardLimit;


    /**
     * PREMIUM등급제한(사용안함)
     */
    @ApiModelProperty("PREMIUM등급제한(사용안함)")
    private Integer premiumLimit;


    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    private Date regdate;


    /**
     * 수정일시
     */
    @ApiModelProperty("수정일시")
    private Date modifyDate;

}

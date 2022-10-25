package com.app.kokonut.activity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class ActivityVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 주키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("주키")
    private Integer idx;


    /**
     * 활동 종류(1:고객정보처리,2:관리자활동,3:회원DB관리이력)
     */
    @ApiModelProperty("활동 종류(1:고객정보처리,2:관리자활동,3:회원DB관리이력)")
    private Integer type;


    /**
     * 활동
     */
    @ApiModelProperty("활동")
    private String activity;


    /**
     * 활동내역(1:로그인,2:회원정보변경,3:회원정보삭제,4:관리자추가,5:관리자권한변경,6:열람이력다운로드,7:활동이력다운로드,8:고객정보 열람,9:고객정보 다운로드,10:고객정보 처리,11:회원정보DB관리 변경,12:회원DB 항목 관리 변경,13:회원 관리 등록,14:정보제공 목록,15:정보 파기 관리,16:테이블 생성,17:전체DB다운로드,18:회원 관리 변경)
     */
    @ApiModelProperty("활동내역(1:로그인,2:회원정보변경,3:회원정보삭제,4:관리자추가,5:관리자권한변경,6:열람이력다운로드,7:활동이력다운로드,8:고객정보 열람,9:고객정보 다운로드,10:고객정보 처리,11:회원정보DB관리 변경,12:회원DB 항목 관리 변경,13:회원 관리 등록,14:정보제공 목록,15:정보 파기 관리,16:테이블 생성,17:전체DB다운로드,18:회원 관리 변경)")
    private Integer month;


    /**
     * 등록일
     */
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 수정일
     */
    @NotNull(message = "modifyDate can not null")
    @ApiModelProperty("수정일")
    private Date modifyDate;

}

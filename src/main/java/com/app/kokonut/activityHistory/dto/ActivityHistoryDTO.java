package com.app.kokonut.activityHistory.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("")
public class ActivityHistoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 회사(Company) 키
     */
    @ApiModelProperty("회사(Company) 키")
    private Integer companyIdx;


    /**
     * 1:고객정보처리,2:관리자활동,3:회원DB관리이력
     */
    @ApiModelProperty("1:고객정보처리,2:관리자활동,3:회원DB관리이력")
    private Integer type;


    /**
     * 관리자키
     */
    @ApiModelProperty("관리자키")
    private Integer adminIdx;


    /**
     * 활동내역(1:로그인,2:회원정보변경,3:회원정보삭제,4:관리자추가,5:관리자권한변경,6:열람이력다운로드,7:활동이력다운로드,8:고객정보 열람,9:고객정보 다운로드,10:고객정보 처리,11:회원정보DB관리 변경,12:회원DB 항목 관리 변경,13:회원 관리 등록,14:정보제공 목록,15:정보 파기 관리,16:테이블 생성,17:전체DB다운로드,18:회원 관리 변경)
     */
    @ApiModelProperty("활동내역(1:로그인,2:회원정보변경,3:회원정보삭제,4:관리자추가,5:관리자권한변경,6:열람이력다운로드,7:활동이력다운로드,8:고객정보 열람,9:고객정보 다운로드,10:고객정보 처리,11:회원정보DB관리 변경,12:회원DB 항목 관리 변경,13:회원 관리 등록,14:정보제공 목록,15:정보 파기 관리,16:테이블 생성,17:전체DB다운로드,18:회원 관리 변경)")
    private Integer activity;


    /**
     * activity IDX
     */
    @ApiModelProperty("activity IDX")
    private Integer activityIdx;


    /**
     * 활동 상세 내역
     */
    @ApiModelProperty("활동 상세 내역")
    private String activityDetail;


    /**
     * 사유
     */
    @ApiModelProperty("사유")
    private String reason;


    /**
     * 접속IP주소
     */
    @ApiModelProperty("접속IP주소")
    private String ipAddr;


    /**
     * 활동일시
     */
    @ApiModelProperty("활동일시")
    private Date regdate;


    /**
     * 0:비정상,1:정상
     */
    @ApiModelProperty("0:비정상,1:정상")
    private Integer state;

}

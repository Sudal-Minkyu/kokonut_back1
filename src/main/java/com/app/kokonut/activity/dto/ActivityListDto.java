package com.app.kokonut.activity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Woody
 * Date : 2022-11-03
 * Time :
 * Remark : Activity 리스트호출 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityListDto {

    /**
     * 주키
     */
    private Integer idx;

    /**
     * 활동
     */
    private String isActivity;

    /**
     * 활동내역(1:로그인,2:회원정보변경,3:회원정보삭제,4:관리자추가,5:관리자권한변경,6:열람이력다운로드,7:활동이력다운로드,8:고객정보 열람,
     * 9:고객정보 다운로드,10:고객정보 처리,11:회원정보DB관리 변경,12:회원DB 항목 관리 변경,
     * 13:회원 관리 등록,14:정보제공 목록,15:정보 파기 관리,16:테이블 생성,17:전체DB다운로드,18:회원 관리 변경)
     */
    private Integer month;

    /**
     * 등록일
     */
    private Date regdate;


    /**
     * 수정일
     */
    private Date modifyDate;

}

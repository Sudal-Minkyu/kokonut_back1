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
public class ActivityDto {

    /**
     * 주키
     */
    private Integer idx;

    /**
     * 활동
     */
    private String isActivity;

    /**
     * 등록일
     */
    private Date regdate;


    /**
     * 수정일
     */
    private Date modifyDate;

}

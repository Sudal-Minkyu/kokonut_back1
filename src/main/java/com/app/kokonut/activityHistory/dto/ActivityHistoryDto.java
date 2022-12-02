package com.app.kokonut.activityHistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Woody
 * Date : 2022-11-03
 * Time :
 * Remark : ActivityHistory 단일 조회 Dto
 * 사용 메서드 : findByActivityHistoryByIdx, findByActivityHistoryByCompanyIdxAndReasonaAtivityIdx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityHistoryDto {

    // activity_hisotroy 테이블
    private Integer idx;
    private Integer companyIdx;
    private Integer adminIdx;
    private Integer activityIdx;
    private String activityDetail;

    private String reason;
    private String ipAddr;

    private Timestamp regdate;
    private Integer state;

    // admin 테이블
    private String maskingName;
    private String name;
    private String email;

    // admin_level 테이블
    private String level;

    // activity 테이블
    private String isActivity;
    private Integer type;
}
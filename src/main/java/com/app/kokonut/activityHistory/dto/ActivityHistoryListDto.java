package com.app.kokonut.activityHistory.dto;

import com.app.kokonut.admin.enums.AuthorityRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Woody
 * Date : 2022-11-03
 * Time :
 * Remark : ActivityHistory 리스트 조회 Dto
 * 사용 메서드 : findByActivityHistoryList
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityHistoryListDto {

    private String knName;
    private String knEmail;

    private AuthorityRole knRoleCode;

    private ActivityCode activityCode;

    private String ahActivityDetail;

//    private String ahReason;

    private LocalDateTime insert_date;

    private String ahIpAddr;

    private Integer ahState;

    public String getKnRoleCode() {
        return knRoleCode.getDesc();
    }

    public String getActivityCode() {
        return activityCode.getDesc();
    }

    public String getInsert_date() {
        return insert_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }

}

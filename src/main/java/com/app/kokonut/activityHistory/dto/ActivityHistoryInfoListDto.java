package com.app.kokonut.activityHistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Woody
 * Date : 2022-11-03
 * Time :
 * Remark : ActivityHistory 정보 조회 Dto
 * 사용 메서드 : findByActivityHistoryByCompanyIdxAndType
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityHistoryInfoListDto {

    // activity_hisotroy 테이블
    private Integer idx;
    private Integer companyIdx;
    private Integer adminId;
    private ActivityCode activityCode;
    private String activityDetail;
    private String reason;
    private String ipAddr;
    private Date regdate;
    private Integer state;

    public String getActivityIdx() {
        return activityCode.getDesc();
    }
}

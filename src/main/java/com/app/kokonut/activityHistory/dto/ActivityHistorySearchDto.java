package com.app.kokonut.activityHistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Woody
 * Date : 2022-11-03
 * Time :
 * Remark : ActivityHistory 리스트 검색 데이터 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityHistorySearchDto {

    private Integer type;

    private Integer activityIdx;

    private Date stimeStart;
    private Date stimeEnd;

    private Long companyId;

    private String searchText;
}

package com.app.kokonut.apiKey.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark : ApiKey 리스트 받아오는 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyListAndDetailDto {

    private Integer idx;
    private Integer companyIdx;
    private Integer adminIdx;
    private String key;
    private Date regdate;

    private Integer type;
    private String note;
    private Date validityStart;
    private Date validityEnd;
    private String beInUse; // 조건문 : LocalDateTime.now() -> BETWEEN validityStart, validityEnd 사이일 경우 "Y" 아니면 "N"

    private Integer useAccumulate;
    private Integer state;
    private String useYn;

    private String reason;
    private Integer modifierIdx;
    private String modifierName;
    private Date modifyDate;

    private String name;
    private String companyName;


}

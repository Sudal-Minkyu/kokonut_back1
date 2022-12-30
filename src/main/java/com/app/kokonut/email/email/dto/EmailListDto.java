package com.app.kokonut.email.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Joy
 * Date : 2022-12-19
 * Time :
 * Remark : Email 목록 조회 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailListDto implements Serializable {
    // email 테이블
    private Integer idx;

    private Integer emailGroupIdx;

    private String title;

    private String contents;

    private LocalDateTime regdate;

    // email_group 테이블
    private String name;
    private String desc;
}

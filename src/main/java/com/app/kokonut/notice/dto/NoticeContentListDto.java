package com.app.kokonut.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author Joy
 * Date : 2022-01-03
 * Time :
 * Remark : 공지사항 목록+내용 조회 시 사용하는 DTO (제목, 내용, 게시일자만 보여주는 간단한 목록 형태)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeContentListDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("내용")
    private String content;

    @ApiModelProperty("게시일자")
    private LocalDateTime registDate;
}
package com.app.kokonut.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

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

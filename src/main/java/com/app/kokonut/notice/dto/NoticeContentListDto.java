package com.app.kokonut.notice.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("")
public class NoticeContentListDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("내용")
    private String content;

    @ApiModelProperty("게시일자")
    private LocalDateTime registDate;
}

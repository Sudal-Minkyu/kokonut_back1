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
public class NoticeSearchDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("상단공지여부(0:일반,1:상단공지)")
    private Integer isNotice;

    private LocalDateTime stimeStart;

    private LocalDateTime stimeEnd;

    private String searchText;  // 검색어

    @ApiModelProperty("0:게시중지,1:게시중,2:게시대기")
    private Integer state;




}

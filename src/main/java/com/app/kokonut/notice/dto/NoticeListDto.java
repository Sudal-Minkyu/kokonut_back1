package com.app.kokonut.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author Joy
 * Date : 2022-01-02
 * Time :
 * Remark : 공지사항 목록 조회 시 사용하는 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListDto implements Serializable {

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("상단공지여부(0:일반,1:상단공지)")
    private Integer isNotice;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("조회수(사용여부확인필요)")
    private Integer viewCount;

    @ApiModelProperty("게시일자")
    private LocalDateTime registDate;

    @ApiModelProperty("등록일자")
    private LocalDateTime regdate;

    @ApiModelProperty("0:게시중지,1:게시중,2:게시대기")
    private Integer state;
}

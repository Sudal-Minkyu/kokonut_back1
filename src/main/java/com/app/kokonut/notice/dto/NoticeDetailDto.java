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
 * Remark : 공지사항 상세 조회 시 사용하는 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("상단공지여부(0:일반,1:상단공지)")
    private Integer isNotice;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("내용")
    private String content;

    @ApiModelProperty("조회수(사용여부확인필요)")
    private Integer viewCount;

    @ApiModelProperty("작성정보 작성자")
    private String registerName;

    @ApiModelProperty("게시일자")
    private LocalDateTime registDate;

    @ApiModelProperty("등록일자")
    private LocalDateTime regdate;

    @ApiModelProperty("수정정보 수정자")
    private String modifierName;

    @ApiModelProperty("수정일자")
    private LocalDateTime modifyDate;

    @ApiModelProperty("0:게시중지,1:게시중,2:게시대기")
    private Integer state;

    @ApiModelProperty("게시중지 일자")
    private LocalDateTime stopDate;

}

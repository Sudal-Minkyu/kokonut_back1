package com.app.kokonut.qna.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Joy
 * Date : 2022-12-28
 * Time :
 * Remark : 1:1 문의글 답변 등록 Dto
 */
@Getter
@Setter
@NoArgsConstructor
public class QnaAnswerSaveDto {

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("상태(0:답변대기,1:답변완료)")
    private Integer state;

    @ApiModelProperty("질문 답변자")
    private Integer ansIdx;

    @ApiModelProperty("답변 내용")
    private String answer;

    @ApiModelProperty("답변일")
    private LocalDateTime answerDate;

}

package com.app.kokonut.qna.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Joy
 * Date : 2022-12-28
 * Time :
 * Remark : Qna 단건 조회 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnaDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;
    // Qna Table
    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("질문자(사용자 키)")
    private Integer adminIdx;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("문의내용")
    private String content;

    @ApiModelProperty("첨부파일 아이디")
    private String fileGroupId;

    @ApiModelProperty("분류(0:기타,1:회원정보,2:사업자정보,3:Kokonut서비스,4:결제)")
    private Integer type;

    @ApiModelProperty("질문등록일시")
    private LocalDateTime regdate;

    @ApiModelProperty("상태(0:답변대기,1:답변완료)")
    private Integer state;

    @ApiModelProperty("답변 내용")
    private String answer;

    @ApiModelProperty("답변일")
    private LocalDateTime answerDate;

    // Admin Table
    @ApiModelProperty("질문자 이메일")
    private String email;

    @ApiModelProperty("질문자명")
    private String maskingName;

    @ApiModelProperty("답변자명")
    private String ansName;
}

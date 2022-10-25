package com.app.kokonut.qna.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class QnaQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * COMPANY IDX
     */
    @ApiModelProperty("COMPANY IDX")
    private Integer companyIdx;


    /**
     * 질문자(사용자 키)
     */
    @ApiModelProperty("질문자(사용자 키)")
    private Integer adminIdx;


    /**
     * 제목
     */
    @ApiModelProperty("제목")
    private String title;


    /**
     * 문의내용
     */
    @ApiModelProperty("문의내용")
    private String content;


    /**
     * 첨부파일 아이디
     */
    @ApiModelProperty("첨부파일 아이디")
    private String fileGroupId;


    /**
     * 분류(0:기타,1:회원정보,2:사업자정보,3:Kokonut서비스,4:결제)
     */
    @ApiModelProperty("분류(0:기타,1:회원정보,2:사업자정보,3:Kokonut서비스,4:결제)")
    private Integer type;


    /**
     * 질문등록일시
     */
    @ApiModelProperty("질문등록일시")
    private Date regdate;


    /**
     * 상태(0:답변대기,1:답변완료)
     */
    @ApiModelProperty("상태(0:답변대기,1:답변완료)")
    private Integer state;


    /**
     * 질문 답변자
     */
    @ApiModelProperty("질문 답변자")
    private Integer ansIdx;


    /**
     * 답변 내용
     */
    @ApiModelProperty("답변 내용")
    private String answer;


    /**
     * 답변일
     */
    @ApiModelProperty("답변일")
    private Date answerDate;

}

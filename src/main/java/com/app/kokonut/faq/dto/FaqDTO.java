package com.app.kokonut.faq.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("")
public class FaqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 최종 등록한 사람의 IDX를 저장
     */
    @ApiModelProperty("최종 등록한 사람의 IDX를 저장")
    private Integer adminIdx;


    /**
     * 질문
     */
    @ApiModelProperty("질문")
    private String question;


    /**
     * 답변
     */
    @ApiModelProperty("답변")
    private String answer;


    /**
     * 분류(0:기타,1:회원정보,2:사업자정보,3:kokonut서비스,4:결제)
     */
    @ApiModelProperty("분류(0:기타,1:회원정보,2:사업자정보,3:kokonut서비스,4:결제)")
    private Integer type;


    /**
     * 작성정보 작성자
     */
    @ApiModelProperty("작성정보 작성자")
    private String registerName;


    /**
     * 등록일자
     */
    @ApiModelProperty("등록일자")
    private Date regdate;


    /**
     * 게시시작일자
     */
    @ApiModelProperty("게시시작일자")
    private Date registStartDate;


    /**
     * 게시종료일자
     */
    @ApiModelProperty("게시종료일자")
    private Date registEndDate;


    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    private Integer modifierIdx;


    /**
     * 수정정보 수정자
     */
    @ApiModelProperty("수정정보 수정자")
    private String modifierName;


    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    private Date modifyDate;


    /**
     * 조회수
     */
    @ApiModelProperty("조회수")
    private Integer viewCount;


    /**
     * 0:게시중지,1:게시중,2:게시대기
     */
    @ApiModelProperty("0:게시중지,1:게시중,2:게시대기")
    private Integer state;


    /**
     * 게시중지 일자
     */
    @ApiModelProperty("게시중지 일자")
    private Date stopDate;

}

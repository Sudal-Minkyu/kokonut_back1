package com.app.kokonut.refactor.faq.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "faq")
public class Faq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @Id
    @ApiModelProperty("키")
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * 최종 등록한 사람의 IDX를 저장
     */
    @Column(name = "ADMIN_IDX")
    @ApiModelProperty("최종 등록한 사람의 IDX를 저장")
    private Integer adminIdx;

    /**
     * 질문
     */
    @ApiModelProperty("질문")
    @Column(name = "QUESTION")
    private String question;

    /**
     * 답변
     */
    @ApiModelProperty("답변")
    @Column(name = "ANSWER")
    private String answer;

    /**
     * 분류(0:기타,1:회원정보,2:사업자정보,3:kokonut서비스,4:결제)
     */
    @Column(name = "TYPE")
    @ApiModelProperty("분류(0:기타,1:회원정보,2:사업자정보,3:kokonut서비스,4:결제)")
    private Integer type;

    /**
     * 작성정보 작성자
     */
    @ApiModelProperty("작성정보 작성자")
    @Column(name = "REGISTER_NAME")
    private String registerName;

    /**
     * 등록일자
     */
    @Column(name = "REGDATE")
    @ApiModelProperty("등록일자")
    private Date regdate;

    /**
     * 게시시작일자
     */
    @ApiModelProperty("게시시작일자")
    @Column(name = "REGIST_START_DATE")
    private Date registStartDate;

    /**
     * 게시종료일자
     */
    @ApiModelProperty("게시종료일자")
    @Column(name = "REGIST_END_DATE")
    private Date registEndDate;

    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    @Column(name = "MODIFIER_IDX")
    private Integer modifierIdx;

    /**
     * 수정정보 수정자
     */
    @ApiModelProperty("수정정보 수정자")
    @Column(name = "MODIFIER_NAME")
    private String modifierName;

    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

    /**
     * 조회수
     */
    @ApiModelProperty("조회수")
    @Column(name = "VIEW_COUNT")
    private Integer viewCount;

    /**
     * 0:게시중지,1:게시중,2:게시대기
     */
    @Column(name = "STATE")
    @ApiModelProperty("0:게시중지,1:게시중,2:게시대기")
    private Integer state;

    /**
     * 게시중지 일자
     */
    @Column(name = "STOP_DATE")
    @ApiModelProperty("게시중지 일자")
    private Date stopDate;

}

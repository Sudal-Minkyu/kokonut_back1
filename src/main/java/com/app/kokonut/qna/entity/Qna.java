package com.app.kokonut.qna.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "qna")
public class Qna implements Serializable {

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
     * COMPANY IDX
     */
    @Column(name = "COMPANY_IDX")
    @ApiModelProperty("COMPANY IDX")
    private Integer companyIdx;

    /**
     * 질문자(사용자 키)
     */
    @Column(name = "ADMIN_IDX")
    @ApiModelProperty("질문자(사용자 키)")
    private Integer adminIdx;

    /**
     * 제목
     */
    @Column(name = "TITLE")
    @ApiModelProperty("제목")
    private String title;

    /**
     * 문의내용
     */
    @Column(name = "CONTENT")
    @ApiModelProperty("문의내용")
    private String content;

    /**
     * 첨부파일 아이디
     */
    @ApiModelProperty("첨부파일 아이디")
    @Column(name = "FILE_GROUP_ID")
    private String fileGroupId;

    /**
     * 분류(0:기타,1:회원정보,2:사업자정보,3:Kokonut서비스,4:결제)
     */
    @Column(name = "TYPE")
    @ApiModelProperty("분류(0:기타,1:회원정보,2:사업자정보,3:Kokonut서비스,4:결제)")
    private Integer type;

    /**
     * 질문등록일시
     */
    @ApiModelProperty("질문등록일시")
    @Column(name = "REGDATE", nullable = false)
    private LocalDateTime regdate;

    /**
     * 상태(0:답변대기,1:답변완료)
     */
    @Column(name = "STATE")
    @ApiModelProperty("상태(0:답변대기,1:답변완료)")
    private Integer state;

    /**
     * 질문 답변자
     */
    @Column(name = "ANS_IDX")
    @ApiModelProperty("질문 답변자")
    private Integer ansIdx;

    /**
     * 답변 내용
     */
    @Column(name = "ANSWER")
    @ApiModelProperty("답변 내용")
    private String answer;

    /**
     * 답변일
     */
    @ApiModelProperty("답변일")
    @Column(name = "ANSWER_DATE")
    private LocalDateTime answerDate;

}

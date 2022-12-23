package com.app.kokonut.email.emailHistory.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "email_history")
public class EmailHistory implements Serializable {

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
     * 보내는 사람 이메일
     */
    @ApiModelProperty("보내는 사람 이메일")
    @Column(name = "FROM", nullable = false)
    private String from;

    /**
     * 보내는 사람 이름
     */
    @Column(name = "FROM_NAME")
    @ApiModelProperty("보내는 사람 이름")
    private String fromName;

    /**
     * 받는 사람 이메일
     */
    @ApiModelProperty("받는 사람 이메일")
    @Column(name = "TO", nullable = false)
    private String to;

    /**
     * 받는 사람 이름
     */
    @Column(name = "TO_NAME")
    @ApiModelProperty("받는 사람 이름")
    private String toName;

    /**
     * 제목
     */
    @ApiModelProperty("제목")
    @Column(name = "TITLE", nullable = false)
    private String title;

    /**
     * 내용
     */
    @ApiModelProperty("내용")
    @Column(name = "CONTENTS", nullable = false)
    private String contents;

    /**
     * 발송일시
     */
    @ApiModelProperty("발송일시")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

}

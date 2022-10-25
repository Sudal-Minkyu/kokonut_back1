package com.app.kokonut.email.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "email")
public class Email implements Serializable {

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
     * 보내는 관리자 키(시스템 관리자 고정)
     */
    @ApiModelProperty("보내는 관리자 키(시스템 관리자 고정)")
    @Column(name = "SENDER_ADMIN_IDX", nullable = false)
    private Integer senderAdminIdx;

    /**
     * 받는사람 타입(I:개별,G:그룹)
     */
    @ApiModelProperty("받는사람 타입(I:개별,G:그룹)")
    @Column(name = "RECEIVER_TYPE", nullable = false)
    private String receiverType;

    /**
     * 받는 관리자 키(문자열, 구분자: ',')
     */
    @Column(name = "RECEIVER_ADMIN_IDX_LIST")
    @ApiModelProperty("받는 관리자 키(문자열, 구분자: ',')")
    private String receiverAdminIdxList;

    /**
     * 받는 그룹 키
     */
    @ApiModelProperty("받는 그룹 키")
    @Column(name = "EMAIL_GROUP_IDX")
    private Integer emailGroupIdx;

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
     * 등록일
     */
    @ApiModelProperty("등록일")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

}

package com.app.kokonut.refactor.addressBook;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EqualsAndHashCode(of = "idx")
@Data
@NoArgsConstructor
@Table(name="address_book")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * 회사 키(주소록 보는 권한이 개인이면 삭제해도 되는 컬럼)
     */
    @Column(name = "COMPANY_IDX")
    @ApiModelProperty("회사 키(주소록 보는 권한이 개인이면 삭제해도 되는 컬럼)")
    private Integer companyIdx;

    /**
     * 관리자 키
     */
    @ApiModelProperty("관리자 키")
    @Column(name = "ADMIN_IDX")
    private Integer adminIdx;

    /**
     * IP
     */
    @Column(name = "IP")
    @ApiModelProperty("IP")
    private String ip;

    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 만료일시
     */
    @ApiModelProperty("만료일시")
    @Column(name = "EXP_DATE", nullable = false)
    private Date expDate;

    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

    /**
     * 발송여부(Y/N)
     */
    @Column(name = "IS_SENDED")
    @ApiModelProperty("발송여부(Y/N)")
    private String sended;

    /**
     * 발송일
     */
    @ApiModelProperty("발송일")
    @Column(name = "SEND_DATE")
    private Date sendDate;

    /**
     * 주소록 용도
     */
    @Column(name = "USE")
    @ApiModelProperty("주소록 용도")
    private String use;

    /**
     * 발송목적(NOTICE: 주요공지, AD:광고/홍보)
     */
    @Column(name = "PURPOSE")
    @ApiModelProperty("발송목적(NOTICE: 주요공지, AD:광고/홍보)")
    private String purpose;

    /**
     * 파일 그룹 아이디
     */
    @ApiModelProperty("파일 그룹 아이디")
    @Column(name = "FILE_GROUP_ID")
    private String fileGroupId;

    /**
     * 발송대상(ALL: 전체회원, SELECTED: 선택회원)
     */
    @Column(name = "TARGET")
    @ApiModelProperty("발송대상(ALL: 전체회원, SELECTED: 선택회원)")
    private String target;

    /**
     * 메시지종류(EMAIL: 이메일, alimTalk ALIMTALK: 알림톡)
     */
    @Column(name = "TYPE")
    @ApiModelProperty("메시지종류(EMAIL: 이메일, alimTalk ALIMTALK: 알림톡)")
    private String type;

    /**
     * 발신자 이메일
     */
    @ApiModelProperty("발신자 이메일")
    @Column(name = "SENDER_EMAIL")
    private String senderEmail;

    /**
     * 메시지 제목
     */
    @Column(name = "TITLE")
    @ApiModelProperty("메시지 제목")
    private String title;

    /**
     * 메시지 내용
     */
    @Column(name = "CONTENT")
    @ApiModelProperty("메시지 내용")
    private String content;

}

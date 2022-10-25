package com.app.kokonut.addressBook.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class AddressBookVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "idx can not null")
    private Integer idx;


    /**
     * 회사 키(주소록 보는 권한이 개인이면 삭제해도 되는 컬럼)
     */
    @ApiModelProperty("회사 키(주소록 보는 권한이 개인이면 삭제해도 되는 컬럼)")
    private Integer companyIdx;


    /**
     * 관리자 키
     */
    @ApiModelProperty("관리자 키")
    private Integer adminIdx;


    /**
     * IP
     */
    @ApiModelProperty("IP")
    private String ip;


    /**
     * 등록일시
     */
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("등록일시")
    private Date regdate;


    /**
     * 만료일시
     */
    @NotNull(message = "expDate can not null")
    @ApiModelProperty("만료일시")
    private Date expDate;


    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    private Date modifyDate;


    /**
     * 발송여부(Y/N)
     */
    @ApiModelProperty("발송여부(Y/N)")
    private String sended;


    /**
     * 발송일
     */
    @ApiModelProperty("발송일")
    private Date sendDate;


    /**
     * 주소록 용도
     */
    @ApiModelProperty("주소록 용도")
    private String use;


    /**
     * 발송목적(NOTICE: 주요공지, AD:광고/홍보)
     */
    @ApiModelProperty("발송목적(NOTICE: 주요공지, AD:광고/홍보)")
    private String purpose;


    /**
     * 파일 그룹 아이디
     */
    @ApiModelProperty("파일 그룹 아이디")
    private String fileGroupId;


    /**
     * 발송대상(ALL: 전체회원, SELECTED: 선택회원)
     */
    @ApiModelProperty("발송대상(ALL: 전체회원, SELECTED: 선택회원)")
    private String target;


    /**
     * 메시지종류(EMAIL: 이메일, alimTalk ALIMTALK: 알림톡)
     */
    @ApiModelProperty("메시지종류(EMAIL: 이메일, alimTalk ALIMTALK: 알림톡)")
    private String type;


    /**
     * 발신자 이메일
     */
    @ApiModelProperty("발신자 이메일")
    private String senderEmail;


    /**
     * 메시지 제목
     */
    @ApiModelProperty("메시지 제목")
    private String title;


    /**
     * 메시지 내용
     */
    @ApiModelProperty("메시지 내용")
    private String content;

}

package com.app.kokonut.alimtalkTemplate.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class AlimtalkTemplateVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 회사 키
     */
    @ApiModelProperty("회사 키")
    private Integer companyIdx;


    /**
     * 채널ID
     */
    @NotNull(message = "channelId can not null")
    @ApiModelProperty("채널ID")
    private String channelId;


    /**
     * 템플릿 코드
     */
    @ApiModelProperty("템플릿 코드")
    private String templateCode;


    /**
     * 템플릿 이름
     */
    @ApiModelProperty("템플릿 이름")
    private String templateName;


    /**
     * 메세지 유형(BA:기본형, EX:부가정보형, AD:광고 추가형, MI:복합형)
     */
    @NotNull(message = "messageType can not null")
    @ApiModelProperty("메세지 유형(BA:기본형, EX:부가정보형, AD:광고 추가형, MI:복합형)")
    private String messageType;


    /**
     * 부가 정보 내용
     */
    @ApiModelProperty("부가 정보 내용")
    private String extraContent;


    /**
     * 광고 추가 내용
     */
    @ApiModelProperty("광고 추가 내용")
    private String adContent;


    /**
     * 알림톡 강조표기 유형(NONE:기본형, TEXT:강조표기형)
     */
    @NotNull(message = "emphasizeType can not null")
    @ApiModelProperty("알림톡 강조표기 유형(NONE:기본형, TEXT:강조표기형)")
    private String emphasizeType;


    /**
     * 알림톡 강조표시 제목
     */
    @ApiModelProperty("알림톡 강조표시 제목")
    private String emphasizeTitle;


    /**
     * 알림톡 강조표시 부제목
     */
    @ApiModelProperty("알림톡 강조표시 부제목")
    private String emphasizeSubTitle;


    /**
     * 보안 설정 여부(0:사용안함,1:사용)
     */
    @ApiModelProperty("보안 설정 여부(0:사용안함,1:사용)")
    private Integer securityFlag;


    /**
     * 등록일시
     */
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("등록일시")
    private Date regdate;


    /**
     * 수정일시
     */
    @ApiModelProperty("수정일시")
    private Date modifyDate;


    /**
     * 상태: ACCEPT - 수락 REGISTER - 등록 INSPECT - 검수 중 COMPLETE - 완료 REJECT - 반려
     */
    @ApiModelProperty("상태: ACCEPT - 수락 REGISTER - 등록 INSPECT - 검수 중 COMPLETE - 완료 REJECT - 반려")
    private String status;

}

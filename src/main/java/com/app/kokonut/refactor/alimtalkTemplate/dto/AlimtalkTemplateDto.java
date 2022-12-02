package com.app.kokonut.refactor.alimtalkTemplate.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Woody
 * Date : 2022-11-29
 * Time :
 * Remark : AlimtalkTemplate 단일 조회 Dto
 * 사용 메서드 :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlimtalkTemplateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
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

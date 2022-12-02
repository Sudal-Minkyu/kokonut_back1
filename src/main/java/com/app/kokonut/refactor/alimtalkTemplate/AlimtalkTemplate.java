package com.app.kokonut.refactor.alimtalkTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "alimtalk_template")
public class AlimtalkTemplate implements Serializable {

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
     * 회사 키
     */
    @ApiModelProperty("회사 키")
    @Column(name = "COMPANY_IDX")
    private Integer companyIdx;

    /**
     * 채널ID
     */
    @ApiModelProperty("채널ID")
    @Column(name = "CHANNEL_ID", nullable = false)
    private String channelId;

    /**
     * 템플릿 코드
     */
    @ApiModelProperty("템플릿 코드")
    @Column(name = "TEMPLATE_CODE")
    private String templateCode;

    /**
     * 템플릿 이름
     */
    @ApiModelProperty("템플릿 이름")
    @Column(name = "TEMPLATE_NAME")
    private String templateName;

    /**
     * 메세지 유형(BA:기본형, EX:부가정보형, AD:광고 추가형, MI:복합형)
     */
    @Column(name = "MESSAGE_TYPE", nullable = false)
    @ApiModelProperty("메세지 유형(BA:기본형, EX:부가정보형, AD:광고 추가형, MI:복합형)")
    private String messageType;

    /**
     * 부가 정보 내용
     */
    @ApiModelProperty("부가 정보 내용")
    @Column(name = "EXTRA_CONTENT")
    private String extraContent;

    /**
     * 광고 추가 내용
     */
    @Column(name = "AD_CONTENT")
    @ApiModelProperty("광고 추가 내용")
    private String adContent;

    /**
     * 알림톡 강조표기 유형(NONE:기본형, TEXT:강조표기형)
     */
    @Column(name = "EMPHASIZE_TYPE", nullable = false)
    @ApiModelProperty("알림톡 강조표기 유형(NONE:기본형, TEXT:강조표기형)")
    private String emphasizeType;

    /**
     * 알림톡 강조표시 제목
     */
    @ApiModelProperty("알림톡 강조표시 제목")
    @Column(name = "EMPHASIZE_TITLE")
    private String emphasizeTitle;

    /**
     * 알림톡 강조표시 부제목
     */
    @ApiModelProperty("알림톡 강조표시 부제목")
    @Column(name = "EMPHASIZE_SUB_TITLE")
    private String emphasizeSubTitle;

    /**
     * 보안 설정 여부(0:사용안함,1:사용)
     */
    @Column(name = "SECURITY_FLAG")
    @ApiModelProperty("보안 설정 여부(0:사용안함,1:사용)")
    private Integer securityFlag;

    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 수정일시
     */
    @ApiModelProperty("수정일시")
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

    /**
     * 상태: ACCEPT - 수락 REGISTER - 등록 INSPECT - 검수 중 COMPLETE - 완료 REJECT - 반려
     */
    @Column(name = "STATUS")
    @ApiModelProperty("상태: ACCEPT - 수락 REGISTER - 등록 INSPECT - 검수 중 COMPLETE - 완료 REJECT - 반려")
    private String status;

}

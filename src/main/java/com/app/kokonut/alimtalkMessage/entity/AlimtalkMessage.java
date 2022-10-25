package com.app.kokonut.alimtalkMessage.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "alimtalk_message")
public class AlimtalkMessage implements Serializable {

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
     * 보낸사람(관리자 키)
     */
    @Column(name = "ADMIN_IDX")
    @ApiModelProperty("보낸사람(관리자 키)")
    private Integer adminIdx;

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
     * 요청ID(예약발송시 reserveId로 사용)
     */
    @Column(name = "REQUEST_ID", nullable = false)
    @ApiModelProperty("요청ID(예약발송시 reserveId로 사용)")
    private String requestId;

    /**
     * 발송타입-즉시발송(immediate),예약발송(reservation)
     */
    @Column(name = "TRANSMIT_TYPE")
    @ApiModelProperty("발송타입-즉시발송(immediate),예약발송(reservation)")
    private String transmitType;

    /**
     * 예약발송일시
     */
    @ApiModelProperty("예약발송일시")
    @Column(name = "RESERVATION_DATE")
    private Date reservationDate;

    /**
     * 발송상태(init-초기상태,[메시지발송요청조회]success-성공,processing-발송중,reserved-예약중,scheduled-스케줄중,fail-실패 [예약메시지]ready-발송 대기,processing-발송 요청중,canceled-발송 취소,fail-발송 요청 실패,done-발송 요청 성공,stale-발송 요청 실패 (시간 초과))
     */
    @Column(name = "STATUS")
    @ApiModelProperty("발송상태(init-초기상태,[메시지발송요청조회]success-성공,processing-발송중,reserved-예약중,scheduled-스케줄중,fail-실패 [예약메시지]ready-발송 대기,processing-발송 요청중,canceled-발송 취소,fail-발송 요청 실패,done-발송 요청 성공,stale-발송 요청 실패 (시간 초과))")
    private String status;

    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 업데이트일시
     */
    @ApiModelProperty("업데이트일시")
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

}

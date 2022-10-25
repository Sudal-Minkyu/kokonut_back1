package com.app.kokonut.friendtalkMessage.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class FriendtalkMessageQueryVO implements Serializable {
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
     * 보낸사람(관리자 키)
     */
    @ApiModelProperty("보낸사람(관리자 키)")
    private Integer adminIdx;


    /**
     * 채널ID
     */
    @ApiModelProperty("채널ID")
    private String channelId;


    /**
     * 요청ID(예약발송시 reserveId로 사용)
     */
    @ApiModelProperty("요청ID(예약발송시 reserveId로 사용)")
    private String requestId;


    /**
     * 발송타입-즉시발송(immediate),예약발송(reservation)
     */
    @ApiModelProperty("발송타입-즉시발송(immediate),예약발송(reservation)")
    private String transmitType;


    /**
     * 예약발송일시
     */
    @ApiModelProperty("예약발송일시")
    private Date reservationDate;


    /**
     * 발송상태(init-초기상태,[메시지발송요청조회]success-성공,processing-발송중,reserved-예약중,scheduled-스케줄중,fail-실패 [예약메시지]ready-발송 대기,processing-발송 요청중,canceled-발송 취소,fail-발송 요청 실패,done-발송 요청 성공,stale-발송 요청 실패 (시간 초과))
     */
    @ApiModelProperty("발송상태(init-초기상태,[메시지발송요청조회]success-성공,processing-발송중,reserved-예약중,scheduled-스케줄중,fail-실패 [예약메시지]ready-발송 대기,processing-발송 요청중,canceled-발송 취소,fail-발송 요청 실패,done-발송 요청 성공,stale-발송 요청 실패 (시간 초과))")
    private String status;


    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    private Date regdate;


    /**
     * 업데이트일시
     */
    @ApiModelProperty("업데이트일시")
    private Date modifyDate;

}

package com.app.kokonut.bizMessage.alimtalkMessage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Woody
 * Date : 2022-12-19
 * Time :
 * Remark : AlimtalkMessage 리스트조회 Dto
 * 사용 메서드 :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlimtalkMessageListDto {

    private String channelId; // 채널ID

    private String templateCode; // 템플릿 코드

    private String requestId; // 요청ID(예약발송시 reserveId로 사용)

    private String status; // 발송상태

    private Date regdate;

}

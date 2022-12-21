package com.app.kokonut.bizMessage.friendtalkMessage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Woody
 * Date : 2022-12-21
 * Time :
 * Remark : FriendtalkMessage 리스트조회 Dto
 * 사용 메서드 :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendtalkMessageListDto {

    private String channelId; // 채널ID

    private String requestId; // 요청ID(예약발송시 reserveId로 사용)

    private String status; // 발송상태

    private Date regdate;

}

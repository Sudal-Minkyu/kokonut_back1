package com.app.kokonut.kakaoChannel.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class KakaoChannelVO implements Serializable {
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
     * 채널 ID
     */
    @NotNull(message = "channelId can not null")
    @ApiModelProperty("채널 ID")
    private String channelId;


    /**
     * 채널 이름
     */
    @ApiModelProperty("채널 이름")
    private String channelName;


    /**
     * 카카오톡 채널 상태(ACTIVE - 정상, DELETED - 삭제, DELETING_PERMANENTLY - 영구 삭제 중, PERMANENTLY_DELETED - 영구 삭제, PENDING_DELETE - 삭제 지연 중, BLOCKED - 차단(반려))
     */
    @ApiModelProperty("카카오톡 채널 상태(ACTIVE - 정상, DELETED - 삭제, DELETING_PERMANENTLY - 영구 삭제 중, PERMANENTLY_DELETED - 영구 삭제, PENDING_DELETE - 삭제 지연 중, BLOCKED - 차단(반려))")
    private String status;


    /**
     * 등록일시
     */
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("등록일시")
    private Date regdate;

}

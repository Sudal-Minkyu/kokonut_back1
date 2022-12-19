package com.app.kokonut.bizMessage.kakaoChannel.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@EqualsAndHashCode(of = "idx")
@Data
@NoArgsConstructor
@Table(name="kakao_channel")
public class KakaoChannel implements Serializable {

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
     * 채널 ID
     */
    @ApiModelProperty("채널 ID")
    @Column(name = "CHANNEL_ID", nullable = false)
    private String channelId;

    /**
     * 채널 이름
     */
    @ApiModelProperty("채널 이름")
    @Column(name = "CHANNEL_NAME")
    private String channelName;

    /**
     * 카카오톡 채널 상태(ACTIVE - 정상, DELETED - 삭제, DELETING_PERMANENTLY - 영구 삭제 중, PERMANENTLY_DELETED - 영구 삭제, PENDING_DELETE - 삭제 지연 중, BLOCKED - 차단(반려))
     */
    @Column(name = "STATUS")
    @ApiModelProperty("카카오톡 채널 상태(ACTIVE - 정상, DELETED - 삭제, DELETING_PERMANENTLY - 영구 삭제 중, PERMANENTLY_DELETED - 영구 삭제, PENDING_DELETE - 삭제 지연 중, BLOCKED - 차단(반려))")
    private String status;

    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    @Column(name = "REGDATE", nullable = false)
    private LocalDateTime regdate;

}

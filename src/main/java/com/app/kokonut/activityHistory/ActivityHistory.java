package com.app.kokonut.activityHistory;

import com.app.kokonut.activityHistory.dto.ActivityCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Woody
 * Date : 2023-01-09
 * Time :
 * Remark : ActivityHistory Table Entity
 */
@Entity
@EqualsAndHashCode(of = "idx")
@Data
@NoArgsConstructor
@Table(name="activity_history")
public class ActivityHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("키")
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;


    // 계정 IDX
    @ApiModelProperty("관리자키")
    @Column(name = "ADMIN_IDX")
    private Integer adminIdx;

    // 회사 IDX
    @Column(name = "COMPANY_IDX")
    @ApiModelProperty("회사(Company) 키")
    private Integer companyIdx;

    // 1:고객정보처리,2:관리자활동,3:회원DB관리이력
    @Column(name = "TYPE")
    @ApiModelProperty("1:고객정보처리,2:관리자활동,3:회원DB관리이력")
    private Integer type;

    // 활동 관리 코드
    @Enumerated(EnumType.STRING)
    @ApiModelProperty("ActivityCode Enum 관리")
    @Column(name="ACTIVITY_CODE")
    private ActivityCode activityCode;

    // 활동 상세 내역
    @ApiModelProperty("활동 상세 내역")
    @Column(name = "ACTIVITY_DETAIL")
    private String activityDetail;

    // 사유
    @ApiModelProperty("사유")
    @Column(name = "REASON")
    private String reason;

    // 접속IP주소
    @Column(name = "IP_ADDR")
    @ApiModelProperty("접속IP주소")
    private String ipAddr;

    // 0:비정상,1:정상
    @Column(name = "STATE")
    @ApiModelProperty("0:비정상,1:정상")
    private Integer state;

    // 활동일시
    @ApiModelProperty("활동일시")
    @Column(name = "REGDATE", nullable = false)
    private LocalDateTime regdate;

}

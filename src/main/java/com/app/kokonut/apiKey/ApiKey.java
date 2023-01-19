package com.app.kokonut.apiKey;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark : api_key Table Entity
 */
@Entity
@EqualsAndHashCode(of = "akId")
@Data
@NoArgsConstructor
@Table(name="kn_api_key")
public class ApiKey implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @Id
    @ApiModelProperty("키")
    @Column(name = "ak_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long akId;

    /**
     * 회사(Company) 키
     */
    @Column(name = "company_id")
    @ApiModelProperty("회사(Company) 키")
    private Long companyId;

    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    @Column(name = "admin_id")
    private Long adminId;

    /**
     * API KEY
     */
    @ApiModelProperty("API KEY")
    @Column(name = "ak_key", nullable = false)
    private String akKey;

    /**
     * 타입(1:일반,2:테스트)
     */
    @Column(name = "ak_type")
    @ApiModelProperty("타입(1:일반,2:테스트)")
    private Integer akType;

    /**
     * 설명
     */
    @Column(name = "ak_note")
    @ApiModelProperty("설명")
    private String akNote;

    /**
     * 유효기한 시작일자
     */
    @ApiModelProperty("유효기한 시작일자")
    @Column(name = "ak_validity_start")
    private LocalDateTime akValidityStart;

    /**
     * 유효기한 종료일자
     */
    @Column(name = "ak_validity_end")
    @ApiModelProperty("유효기한 종료일자")
    private LocalDateTime akValidityEnd;

    /**
     * 테스트기간 누적데이터 지속사용여부(0:일괄삭제,1:지속사용)
     */
    @Column(name = "ak_use_accumulate")
    @ApiModelProperty("테스트기간 누적데이터 지속사용여부(0:일괄삭제,1:지속사용)")
    private Integer akUseAccumulate;

    /**
     * 발급상태(1:신규,2:재발급)
     */
    @Column(name = "ak_state")
    @ApiModelProperty("발급상태(1:신규,2:재발급)")
    private Integer akState;

    /**
     * 사용여부
     */
    @Column(name = "ak_use_yn")
    @ApiModelProperty("사용여부")
    private String akUseYn;

    /**
     * 해제사유
     */
    @Column(name = "ak_reason")
    @ApiModelProperty("해제사유")
    private String akReason;

    /**
     * 등록자 email
     */
    @ApiModelProperty("등록자 email")
    @Column(name = "insert_email", nullable = false)
    private String insert_email;

    /**
     * 등록 날짜
     */
    @ApiModelProperty("등록 날짜")
    @Column(name = "insert_date", nullable = false)
    private LocalDateTime insert_date;


    /**
     * 수정자
     */
    @ApiModelProperty("수정자 id")
    @Column(name = "modify_id")
    private Long modify_id;

    /**
     * 수정자 이름
     */
    @ApiModelProperty("수정자 email")
    @Column(name = "modify_email")
    private String modify_email;

    /**
     * 수정 날짜
     */
    @ApiModelProperty("수정 날짜")
    @Column(name = "modify_date")
    private LocalDateTime modify_date;

}

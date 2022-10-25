package com.app.kokonut.apiKey.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "api_key")
public class ApiKey implements Serializable {

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
     * 회사(Company) 키
     */
    @Column(name = "COMPANY_IDX")
    @ApiModelProperty("회사(Company) 키")
    private Integer companyIdx;

    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    @Column(name = "ADMIN_IDX")
    private Integer adminIdx;

    /**
     * 등록자 이름
     */
    @ApiModelProperty("등록자 이름")
    @Column(name = "REGISTER_NAME")
    private String registerName;

    /**
     * API KEY
     */
    @ApiModelProperty("API KEY")
    @Column(name = "KEY", nullable = false)
    private String key;

    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 타입(1:일반,2:테스트)
     */
    @Column(name = "TYPE")
    @ApiModelProperty("타입(1:일반,2:테스트)")
    private Integer type;

    /**
     * 설명
     */
    @Column(name = "NOTE")
    @ApiModelProperty("설명")
    private String note;

    /**
     * 유효기한 시작일자
     */
    @ApiModelProperty("유효기한 시작일자")
    @Column(name = "VALIDITY_START")
    private Date validityStart;

    /**
     * 유효기한 종료일자
     */
    @Column(name = "VALIDITY_END")
    @ApiModelProperty("유효기한 종료일자")
    private Date validityEnd;

    /**
     * 테스트기간 누적데이터 지속사용여부(0:일괄삭제,1:지속사용)
     */
    @Column(name = "USE_ACCUMULATE")
    @ApiModelProperty("테스트기간 누적데이터 지속사용여부(0:일괄삭제,1:지속사용)")
    private Integer useAccumulate;

    /**
     * 발급상태(1:신규,2:재발급)
     */
    @Column(name = "STATE")
    @ApiModelProperty("발급상태(1:신규,2:재발급)")
    private Integer state;

    /**
     * 사용여부
     */
    @Column(name = "USE_YN")
    @ApiModelProperty("사용여부")
    private String useYn;

    /**
     * 해제사유
     */
    @Column(name = "REASON")
    @ApiModelProperty("해제사유")
    private String reason;

    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    @Column(name = "MODIFIER_IDX")
    private Integer modifierIdx;

    /**
     * 수정자 이름
     */
    @ApiModelProperty("수정자 이름")
    @Column(name = "MODIFIER_NAME")
    private String modifierName;

    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

}

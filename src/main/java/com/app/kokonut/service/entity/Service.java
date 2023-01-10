package com.app.kokonut.service.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "kn_service")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @Id
    @ApiModelProperty("키")
    @Column(name = "idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * 서비스 이름
     */
    @ApiModelProperty("서비스 이름")
    @Column(name = "ks_service")
    private String ksService;

    /**
     * 서비스 금액
     */
    @ApiModelProperty("서비스 금액")
    @Column(name = "ks_price")
    private Integer ksPrice;

    /**
     * 평균 회원 1명당 금액
     */
    @ApiModelProperty("평균 회원 1명당 금액")
    @Column(name = "ks_per_price")
    private Integer ksPerPrice;

    /**
     * 등록일자
     */
    @ApiModelProperty("등록일자")
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

}

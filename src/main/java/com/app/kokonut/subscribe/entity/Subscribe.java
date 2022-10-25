package com.app.kokonut.subscribe.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "subscribe")
public class Subscribe implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 주키
     */
    @Id
    @ApiModelProperty("주키")
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * 서비스 이름
     */
    @Column(name = "SERVICE")
    @ApiModelProperty("서비스 이름")
    private String service;

    /**
     * 서비스 금액
     */
    @Column(name = "PRICE")
    @ApiModelProperty("서비스 금액")
    private Integer price;

    /**
     * 평균 회원 1명당 금액
     */
    @Column(name = "PER_PRICE")
    @ApiModelProperty("평균 회원 1명당 금액")
    private Integer perPrice;

    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 수정일
     */
    @ApiModelProperty("수정일")
    @Column(name = "MODIFY_DATE", nullable = false)
    private Date modifyDate;

}

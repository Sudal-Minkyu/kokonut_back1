package com.app.kokonut.service.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "service")
public class Service implements Serializable {

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
     * 서비스 이름
     */
    @Column(name = "SERVICE")
    @ApiModelProperty("서비스 이름")
    private String isService;

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
     * 등록일자
     */
    @Column(name = "REGDATE")
    @ApiModelProperty("등록일자")
    private LocalDateTime regdate;

    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

}

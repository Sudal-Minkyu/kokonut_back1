package com.app.kokonut.refactor.shedlock.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 스케줄잠금
 */
@Data
@Entity
@ApiModel("스케줄잠금")
@Table(name = "shedlock")
public class Shedlock implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 스케줄잠금이름
     */
    @Id
    @ApiModelProperty("스케줄잠금이름")
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 잠금기간
     */
    @ApiModelProperty("잠금기간")
    @Column(name = "lock_until")
    private Date lockUntil;

    /**
     * 잠금일시
     */
    @ApiModelProperty("잠금일시")
    @Column(name = "locked_at")
    private Date lockedAt;

    /**
     * 잠금신청자
     */
    @ApiModelProperty("잠금신청자")
    @Column(name = "locked_by")
    private String lockedBy;

}

package com.app.kokonut.awsKmsHistory.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "aws_kms_history")
public class AwsKmsHistory implements Serializable {

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
     * 호출 날짜
     */
    @ApiModelProperty("호출 날짜")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 호출 타입
     */
    @Column(name = "TYPE")
    @ApiModelProperty("호출 타입")
    private String type;

}

package com.app.kokonut.awsKmsHistory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@EqualsAndHashCode(of = "idx")
@Data
@NoArgsConstructor
@Table(name="aws_kms_history")
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
     * 호출 타입
     */
    @Column(name = "TYPE")
    @ApiModelProperty("호출 타입")
    private String type;

    /**
     * 호출 날짜
     */
    @ApiModelProperty("호출 날짜")
    @Column(name = "REGDATE", nullable = false)
    private LocalDateTime regdate;

}

package com.app.kokonut.refactor.log4jHistory.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "log4j_history")
public class Log4jHistory implements Serializable {

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
     * 발생일시
     */
    @ApiModelProperty("발생일시")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * API_KEY 키
     */
    @Column(name = "API_KEY_IDX")
    @ApiModelProperty("API_KEY 키")
    private Integer apiKeyIdx;

    /**
     * IP
     */
    @Column(name = "IP")
    @ApiModelProperty("IP")
    private String ip;

    /**
     * 유형(CREATE, READ, UPDATE, DELETE, DOWNLOAD)
     */
    @Column(name = "TYPE")
    @ApiModelProperty("유형(CREATE, READ, UPDATE, DELETE, DOWNLOAD)")
    private String type;

    /**
     * 로그 레벨(FATAL, ERROR, WARN, INFO, DEBUG, TRACE)
     */
    @Column(name = "LEVEL")
    @ApiModelProperty("로그 레벨(FATAL, ERROR, WARN, INFO, DEBUG, TRACE)")
    private String level;

    /**
     * 내용
     */
    @ApiModelProperty("내용")
    @Column(name = "MESSAGE")
    private String message;

    /**
     * Logger 이름
     */
    @Column(name = "LOGGER")
    @ApiModelProperty("Logger 이름")
    private String logger;

}

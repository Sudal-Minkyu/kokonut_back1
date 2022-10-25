package com.app.kokonut.log4jHistory.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("")
public class Log4jHistoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 발생일시
     */
    @ApiModelProperty("발생일시")
    private Date regdate;


    /**
     * API_KEY 키
     */
    @ApiModelProperty("API_KEY 키")
    private Integer apiKeyIdx;


    /**
     * IP
     */
    @ApiModelProperty("IP")
    private String ip;


    /**
     * 유형(CREATE, READ, UPDATE, DELETE, DOWNLOAD)
     */
    @ApiModelProperty("유형(CREATE, READ, UPDATE, DELETE, DOWNLOAD)")
    private String type;


    /**
     * 로그 레벨(FATAL, ERROR, WARN, INFO, DEBUG, TRACE)
     */
    @ApiModelProperty("로그 레벨(FATAL, ERROR, WARN, INFO, DEBUG, TRACE)")
    private String level;


    /**
     * 내용
     */
    @ApiModelProperty("내용")
    private String message;


    /**
     * Logger 이름
     */
    @ApiModelProperty("Logger 이름")
    private String logger;

}

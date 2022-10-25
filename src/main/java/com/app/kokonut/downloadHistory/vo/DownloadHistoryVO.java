package com.app.kokonut.downloadHistory.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class DownloadHistoryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 종류(ACTIVITY:활동이력)
     */
    @ApiModelProperty("종류(ACTIVITY:활동이력)")
    private String type;


    /**
     * 다운로드한 파일 이름
     */
    @ApiModelProperty("다운로드한 파일 이름")
    private String fileName;


    /**
     * 다운로드 사유
     */
    @ApiModelProperty("다운로드 사유")
    private String reason;


    /**
     * 다운로드한 사람
     */
    @ApiModelProperty("다운로드한 사람")
    private Integer adminIdx;


    /**
     * 다운로드한 사람 이름
     */
    @ApiModelProperty("다운로드한 사람 이름")
    private String registerName;


    /**
     * 다운로드 일시
     */
    @NotNull(message = "registDate can not null")
    @ApiModelProperty("다운로드 일시")
    private Date registDate;

}

package com.app.kokonut.refactor.downloadHistory.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "download_history")
public class DownloadHistory implements Serializable {

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
     * 종류(ACTIVITY:활동이력)
     */
    @Column(name = "TYPE")
    @ApiModelProperty("종류(ACTIVITY:활동이력)")
    private String type;

    /**
     * 다운로드한 파일 이름
     */
    @Column(name = "FILE_NAME")
    @ApiModelProperty("다운로드한 파일 이름")
    private String fileName;

    /**
     * 다운로드 사유
     */
    @Column(name = "REASON")
    @ApiModelProperty("다운로드 사유")
    private String reason;

    /**
     * 다운로드한 사람
     */
    @Column(name = "ADMIN_IDX")
    @ApiModelProperty("다운로드한 사람")
    private Integer adminIdx;

    /**
     * 다운로드한 사람 이름
     */
    @Column(name = "REGISTER_NAME")
    @ApiModelProperty("다운로드한 사람 이름")
    private String registerName;

    /**
     * 다운로드 일시
     */
    @ApiModelProperty("다운로드 일시")
    @Column(name = "REGIST_DATE", nullable = false)
    private Date registDate;

}

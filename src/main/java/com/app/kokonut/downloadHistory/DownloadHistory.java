package com.app.kokonut.downloadHistory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode(of = "idx")
@Data
@NoArgsConstructor
@Table(name="download_history")
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
    private Integer adminId;

    /**
     * 다운로드 일시
     */
    @ApiModelProperty("다운로드 일시")
    @Column(name = "REGIST_DATE", nullable = false)
    private LocalDateTime registDate;

}

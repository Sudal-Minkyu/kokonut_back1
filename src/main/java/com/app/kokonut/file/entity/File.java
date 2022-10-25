package com.app.kokonut.file.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "file")
public class File implements Serializable {

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
     * 파일그룹아이디
     */
    @ApiModelProperty("파일그룹아이디")
    @Column(name = "FILE_GROUP_ID")
    private String fileGroupId;

    /**
     * 파일사이즈
     */
    @ApiModelProperty("파일사이즈")
    @Column(name = "FILE_SIZE")
    private Integer fileSize;

    /**
     * 실제파일이름
     */
    @ApiModelProperty("실제파일이름")
    @Column(name = "REAL_FILE_NAME")
    private String realFileName;

    /**
     * 저장시파일이름
     */
    @ApiModelProperty("저장시파일이름")
    @Column(name = "SAVE_FILE_NAME")
    private String saveFileName;

    /**
     * 파일저장경로
     */
    @Column(name = "FILE_PATH")
    @ApiModelProperty("파일저장경로")
    private String filePath;

    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    @Column(name = "ADMIN_IDX")
    private Integer adminIdx;

    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    @Column(name = "REGDATE")
    private Date regdate;

    /**
     * 코멘트
     */
    @ApiModelProperty("코멘트")
    @Column(name = "COMMENT")
    private String comment;

}

package com.app.kokonut.file.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class FileQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 주키
     */
    @ApiModelProperty("주키")
    private Integer idx;


    /**
     * 파일그룹아이디
     */
    @ApiModelProperty("파일그룹아이디")
    private String fileGroupId;


    /**
     * 파일사이즈
     */
    @ApiModelProperty("파일사이즈")
    private Integer fileSize;


    /**
     * 실제파일이름
     */
    @ApiModelProperty("실제파일이름")
    private String realFileName;


    /**
     * 저장시파일이름
     */
    @ApiModelProperty("저장시파일이름")
    private String saveFileName;


    /**
     * 파일저장경로
     */
    @ApiModelProperty("파일저장경로")
    private String filePath;


    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    private Integer adminIdx;


    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    private Date regdate;


    /**
     * 코멘트
     */
    @ApiModelProperty("코멘트")
    private String comment;

}

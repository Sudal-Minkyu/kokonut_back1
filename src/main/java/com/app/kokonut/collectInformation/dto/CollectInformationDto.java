package com.app.kokonut.collectInformation.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Joy
 * Date : 2023-01-94
 * Time :
 * Remark : 기본 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectInformationDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 주키
     */
    @ApiModelProperty("주키")
    private Integer idx;


    /**
     * 회사 IDX
     */
    @ApiModelProperty("회사 IDX")
    private Integer companyIdx;


    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    private Integer adminIdx;


    /**
     * 제목
     */
    @ApiModelProperty("제목")
    private String title;


    /**
     * 내용
     */
    @ApiModelProperty("내용")
    private String content;


    /**
     * 등록자 이름
     */
    @ApiModelProperty("등록자 이름")
    private String registerName;


    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    private LocalDateTime regdate;


    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    private Integer modifierIdx;


    /**
     * 수정자 이름
     */
    @ApiModelProperty("수정자 이름")
    private String modifierName;


    /**
     * 수정일
     */
    @ApiModelProperty("수정일")
    private LocalDateTime modifyDate;

}

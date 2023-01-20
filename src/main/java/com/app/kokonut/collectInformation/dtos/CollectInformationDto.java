package com.app.kokonut.collectInformation.dtos;
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

    @ApiModelProperty("주키")
    private Integer idx;

    @ApiModelProperty("회사 IDX")
    private Long companyId;

    @ApiModelProperty("등록자")
    private Long adminId;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("내용")
    private String content;

    @ApiModelProperty("등록자 이름")
    private String registerName;

    @ApiModelProperty("등록일")
    private LocalDateTime regdate;

    @ApiModelProperty("수정자")
    private Integer modifierIdx;

    @ApiModelProperty("수정자 이름")
    private String modifierName;

    @ApiModelProperty("수정일")
    private LocalDateTime modifyDate;

}

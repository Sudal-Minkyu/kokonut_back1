package com.app.kokonut.collectInformation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Joy
 * Date : 2023-01-94
 * Time :
 * Remark : 개인정보 처리방침 - 개인정보 수집 및 이용 안내 상세보기를 위한 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectInfoDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("주키")
    private Integer idx;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("내용")
    private String content;

}

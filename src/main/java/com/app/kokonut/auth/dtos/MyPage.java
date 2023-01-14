package com.app.kokonut.auth.dtos;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Woody
 * Date : 2023-01-14
 * Time :
 * Remark : Pageable 테스트를 위한 Page 클래스
 */
public class MyPage {

    @ApiModelProperty(value = "페이지 번호")
    private Integer page;

    @ApiModelProperty(value = "페이지 크기")
    private Integer size;

    @ApiModelProperty(value = "정렬")
    private List<String> sort;

}

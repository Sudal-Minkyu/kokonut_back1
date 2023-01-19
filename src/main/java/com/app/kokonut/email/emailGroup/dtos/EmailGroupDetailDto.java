package com.app.kokonut.email.emailGroup.dtos;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Joy
 * Date : 2022-12-22
 * Time :
 * Remark : EmailGroup 저장, 수정시 사용하는 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailGroupDetailDto implements Serializable {

    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;

    /**
     * 관리자 키(문자열, 구분자: ',')
     */
    @ApiModelProperty("관리자 키(문자열, 구분자: ',')")
    private String adminIdList;


    /**
     * 그룹명
     */
    @ApiModelProperty("그룹명")
    private String name;


    /**
     * 그룹설명
     */
    @ApiModelProperty("그룹설명")
    private String desc;
}

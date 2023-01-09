package com.app.kokonut.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Joy
 * Date : 2023-01-09
 * Time :
 * Remark : 서비스 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto implements Serializable {

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("서비스 이름")
    private String isService;

    @ApiModelProperty("서비스 금액")
    private Integer price;

    @ApiModelProperty("평균 회원 1명당 금액")
    private Integer perPrice;

}

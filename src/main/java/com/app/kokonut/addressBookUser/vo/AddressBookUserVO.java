package com.app.kokonut.addressBookUser.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class AddressBookUserVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 주소록 키
     */
    @ApiModelProperty("주소록 키")
    private Integer addressBookIdx;


    /**
     * 아이디
     */
    @ApiModelProperty("아이디")
    private String id;


    /**
     * 등록일시
     */
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("등록일시")
    private Date regdate;

}

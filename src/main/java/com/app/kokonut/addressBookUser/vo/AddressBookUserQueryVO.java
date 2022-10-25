package com.app.kokonut.addressBookUser.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class AddressBookUserQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
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
    @ApiModelProperty("등록일시")
    private Date regdate;

}

package com.app.kokonut.adminRemove.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("")
public class AdminRemoveDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ADMIN IDX
     */
    @ApiModelProperty("ADMIN IDX")
    private Integer adminIdx;


    /**
     * 이메일
     */
    @ApiModelProperty("이메일")
    private String email;


    /**
     * 탈퇴 사유(1:계정변경, 2:서비스 이용 불만, 3:사용하지 않음, 4:기타)
     */
    @ApiModelProperty("탈퇴 사유(1:계정변경, 2:서비스 이용 불만, 3:사용하지 않음, 4:기타)")
    private Integer reason;


    /**
     * 탈퇴 사유
     */
    @ApiModelProperty("탈퇴 사유")
    private String reasonDetail;


    /**
     * 탈퇴일시
     */
    @ApiModelProperty("탈퇴일시")
    private Date regdate;

}

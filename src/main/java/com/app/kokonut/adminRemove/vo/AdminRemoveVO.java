package com.app.kokonut.adminRemove.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class AdminRemoveVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * ADMIN IDX
     */
    @NotNull(message = "adminIdx can not null")
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
    @NotNull(message = "regdate can not null")
    @ApiModelProperty("탈퇴일시")
    private Date regdate;

}

package com.app.kokonut.refactor.privacyEmailHistory.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class PrivacyEmailHistoryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * privacy_email 키
     */
    @NotNull(message = "privacyEmailIdx can not null")
    @ApiModelProperty("privacy_email 키")
    private Integer privacyEmailIdx;


    /**
     * 받는 사람 이메일
     */
    @NotNull(message = "receiverEmail can not null")
    @ApiModelProperty("받는 사람 이메일")
    private String receiverEmail;


    /**
     * 발송일
     */
    @NotNull(message = "sendDate can not null")
    @ApiModelProperty("발송일")
    private Date sendDate;

}

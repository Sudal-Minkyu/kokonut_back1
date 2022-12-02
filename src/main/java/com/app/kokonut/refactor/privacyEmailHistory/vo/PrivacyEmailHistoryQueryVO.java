package com.app.kokonut.refactor.privacyEmailHistory.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class PrivacyEmailHistoryQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * privacy_email 키
     */
    @ApiModelProperty("privacy_email 키")
    private Integer privacyEmailIdx;


    /**
     * 받는 사람 이메일
     */
    @ApiModelProperty("받는 사람 이메일")
    private String receiverEmail;


    /**
     * 발송일
     */
    @ApiModelProperty("발송일")
    private Date sendDate;

}

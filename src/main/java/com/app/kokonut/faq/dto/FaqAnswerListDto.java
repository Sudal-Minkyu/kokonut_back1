package com.app.kokonut.faq.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("")
public class FaqAnswerListDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("질문")
    private String question;

    @ApiModelProperty("답변")
    private String answer;

}

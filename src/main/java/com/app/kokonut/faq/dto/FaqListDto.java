package com.app.kokonut.faq.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaqListDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("질문")
    private String question;

    @ApiModelProperty("답변")
    private String answer;

    @ApiModelProperty("분류(0:기타,1:회원정보,2:사업자정보,3:kokonut서비스,4:결제)")
    private Integer type;

    @ApiModelProperty("등록일자")
    private LocalDateTime regdate;

    @ApiModelProperty("조회수")
    private Integer viewCount;

}

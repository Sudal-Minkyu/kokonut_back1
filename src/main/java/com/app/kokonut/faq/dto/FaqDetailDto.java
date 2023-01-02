package com.app.kokonut.faq.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@ApiModel("")
public class FaqDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("등록자")
    private Integer adminIdx;

    @ApiModelProperty("질문")
    private String question;

    @ApiModelProperty("답변")
    private String answer;

    @ApiModelProperty("분류(0:기타,1:회원정보,2:사업자정보,3:kokonut서비스,4:결제)")
    private Integer type;

    @ApiModelProperty("작성정보 작성자")
    private String registerName;

    @ApiModelProperty("등록일자")
    private LocalDateTime regdate;

    @ApiModelProperty("수정자")
    private Integer modifierIdx;

    @ApiModelProperty("수정정보 수정자")
    private String modifierName;

    @ApiModelProperty("수정일자")
    private LocalDateTime modifyDate;

    @ApiModelProperty("조회수")
    private Integer viewCount;

}

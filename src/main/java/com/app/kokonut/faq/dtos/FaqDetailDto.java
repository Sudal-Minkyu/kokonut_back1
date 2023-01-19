package com.app.kokonut.faq.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaqDetailDto implements Serializable {

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("등록자")
    private Integer adminId;

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

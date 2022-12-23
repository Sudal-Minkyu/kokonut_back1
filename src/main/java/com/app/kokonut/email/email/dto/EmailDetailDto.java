package com.app.kokonut.email.email.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Joy
 * Date : 2022-12-19
 * Time :
 * Remark : Email 단일조회 Dto, Email 전송 저장용 Dto
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetailDto {

    // email 테이블
    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("받는사람 타입(I:개별,G:그룹)")
    private String receiverType;

    @ApiModelProperty("받는 관리자 키(문자열, 구분자: ',')")
    private String receiverAdminIdxList;

    @ApiModelProperty("받는 그룹 키")
    private Integer emailGroupIdx;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("내용")
    private String contents;

}
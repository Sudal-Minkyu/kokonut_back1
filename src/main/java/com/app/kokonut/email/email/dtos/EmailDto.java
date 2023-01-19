package com.app.kokonut.email.email.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * @author Joy
 * Date : 2022-12-19
 * Time :
 * Remark : Email 단일조회 Dto
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;

    /**
     * 보내는 관리자 키(시스템 관리자 고정)
     */
    @ApiModelProperty("보내는 관리자 키(시스템 관리자 고정)")
    private Integer senderadminId;

    /**
     * 받는사람 타입(I:개별,G:그룹)
     */
    @ApiModelProperty("받는사람 타입(I:개별,G:그룹)")
    private String receiverType;

    /**
     * 받는 관리자 키(문자열, 구분자: ',')
     */
    @ApiModelProperty("받는 관리자 키(문자열, 구분자: ',')")
    private String receiveradminIdList;

    /**
     * 받는 그룹 키
     */
    @ApiModelProperty("받는 그룹 키")
    private Integer emailGroupIdx;

    /**
     * 제목
     */
    @ApiModelProperty("제목")
    private String title;

    /**
     * 내용
     */
    @ApiModelProperty("내용")
    private String contents;

    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    private LocalDateTime regdate;
}
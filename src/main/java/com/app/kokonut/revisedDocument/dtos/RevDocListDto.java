package com.app.kokonut.revisedDocument.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Joy
 * Date : 2023-01-94
 * Time :
 * Remark : 개인정보 처리방침 - 처리방침 문서 목록 조회를 위한 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevDocListDto implements Serializable {

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("시행시작일자")
    private LocalDateTime enforceStartDate;

    @ApiModelProperty("시행종료일자")
    private LocalDateTime enforceEndDate;

    @ApiModelProperty("등록자이름")
    private String registerName;

    @ApiModelProperty("등록일자")
    private LocalDateTime regdate;

    // reviseDocumentFile 테이블

//    TODO 공통 키 작업 완료 후 테스트 코드 작성 후 추가 개발
//    @ApiModelProperty("원래 파일명")
//    private String cfOriginalFilename;

}

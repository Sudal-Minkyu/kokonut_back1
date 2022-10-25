package com.app.kokonut.apiKey.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark : "Key"를 통해 데이터받아오는 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyListDto {

    private Integer idx;
    private Integer companyIdx;
    private Integer adminIdx;


    /**
     * 등록자 이름
     */
    @ApiModelProperty("등록자 이름")
    private String registerName;


    /**
     * API KEY
     */
    @ApiModelProperty("API KEY")
    private String key;


    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    private Date regdate;


    /**
     * 타입(1:일반,2:테스트)
     */
    @ApiModelProperty("타입(1:일반,2:테스트)")
    private Integer type;


    /**
     * 설명
     */
    @ApiModelProperty("설명")
    private String note;


    /**
     * 유효기한 시작일자
     */
    @ApiModelProperty("유효기한 시작일자")
    private Date validityStart;


    /**
     * 유효기한 종료일자
     */
    @ApiModelProperty("유효기한 종료일자")
    private Date validityEnd;


    /**
     * 테스트기간 누적데이터 지속사용여부(0:일괄삭제,1:지속사용)
     */
    @ApiModelProperty("테스트기간 누적데이터 지속사용여부(0:일괄삭제,1:지속사용)")
    private Integer useAccumulate;


    /**
     * 발급상태(1:신규,2:재발급)
     */
    @ApiModelProperty("발급상태(1:신규,2:재발급)")
    private Integer state;


    /**
     * 사용여부
     */
    @ApiModelProperty("사용여부")
    private String useYn;


    /**
     * 해제사유
     */
    @ApiModelProperty("해제사유")
    private String reason;


    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    private Integer modifierIdx;


    /**
     * 수정자 이름
     */
    @ApiModelProperty("수정자 이름")
    private String modifierName;


    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    private Date modifyDate;

    // Date 변환
//    public String getFrInsertDate() {
//        return frInsertDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//    }

}

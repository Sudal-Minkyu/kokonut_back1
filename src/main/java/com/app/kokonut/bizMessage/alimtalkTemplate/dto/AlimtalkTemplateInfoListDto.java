package com.app.kokonut.bizMessage.alimtalkTemplate.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Woody
 * Date : 2022-12-15
 * Time :
 * Remark : AlimtalkTemplate 정보조회용 리스트 Dto
 * 사용 메서드 : templateCode, status 만 필요 추후 추가 될 가능성 있음
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlimtalkTemplateInfoListDto {

    private String templateCode;

    private String status;

}

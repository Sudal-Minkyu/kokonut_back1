package com.app.kokonut.bizMessage.alimtalkTemplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Woody
 * Date : 2022-12-20
 * Time :
 * Remark : AlimtalkMessage의 템플릿 정보조회용 리스트 Dto
 * 사용 메서드 :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlimtalkMessageTemplateInfoListDto {

    private String templateCode;

    private String messageType;

    private String extraContent;

    private String adContent;

    private String emphasizeType;

    private String emphasizeTitle;

    private String emphasizeSubTitle;

}

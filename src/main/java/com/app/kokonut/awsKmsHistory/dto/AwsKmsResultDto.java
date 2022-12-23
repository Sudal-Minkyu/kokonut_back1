package com.app.kokonut.awsKmsHistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Woody
 * Date : 2022-12-22
 * Time :
 * Remark : AwsKms 결과 반환 Dto -> decryptText, encryptText, dataKey,
 * 사용 메서드 :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwsKmsResultDto {

    private String result;

    private String encryptText;

    private String dataKey;

    private String decryptText;

}

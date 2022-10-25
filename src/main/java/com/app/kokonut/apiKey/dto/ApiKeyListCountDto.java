package com.app.kokonut.apiKey.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark : ApiKey 리스트의 Count 받아오는 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyListCountDto {

    private Long count;

}

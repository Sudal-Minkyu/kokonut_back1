package com.app.kokonut.refactor.alimtalkMessageRecipient.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Woody
 * Date : 2022-11-29
 * Time :
 * Remark : AlimtalkMessageRecipient 단일 조회 Dto
 * 사용 메서드 :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlimtalkMessageRecipientDto implements Serializable {

    private Integer idx;

    private Integer alimtalkMessageIdx;

    private String email;

    private String name;

    private String phoneNumber;

}

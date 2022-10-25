package com.app.kokonut.alimtalkMessageRecipient.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("")
public class AlimtalkMessageRecipientDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer idx;

    private Integer alimtalkMessageIdx;

    private String email;

    private String name;

    private String phoneNumber;

}

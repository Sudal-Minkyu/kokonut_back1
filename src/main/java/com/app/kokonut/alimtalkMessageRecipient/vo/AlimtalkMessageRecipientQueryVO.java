package com.app.kokonut.alimtalkMessageRecipient.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("Retrieve by query ")
public class AlimtalkMessageRecipientQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idx;

    private Integer alimtalkMessageIdx;

    private String email;

    private String name;

    private String phoneNumber;

}

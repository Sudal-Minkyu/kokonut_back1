package com.app.kokonut.alimtalkMessageRecipient.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ApiModel("Save ")
public class AlimtalkMessageRecipientVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "idx can not null")
    private Integer idx;

    private Integer alimtalkMessageIdx;

    private String email;

    private String name;

    private String phoneNumber;

}

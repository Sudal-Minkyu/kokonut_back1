package com.app.kokonut.refactor.friendtalkMessageRecipient.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ApiModel("Save ")
public class FriendtalkMessageRecipientVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "idx can not null")
    private Integer idx;

    private Integer friendtalkMessageIdx;

    private String email;

    private String name;

    private String phoneNumber;

}

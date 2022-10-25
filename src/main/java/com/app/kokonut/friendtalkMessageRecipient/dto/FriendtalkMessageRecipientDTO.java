package com.app.kokonut.friendtalkMessageRecipient.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("")
public class FriendtalkMessageRecipientDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer idx;

    private Integer friendtalkMessageIdx;

    private String email;

    private String name;

    private String phoneNumber;

}

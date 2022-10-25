package com.app.kokonut.friendtalkMessageRecipient.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel("Update ")
@EqualsAndHashCode(callSuper = false)
public class FriendtalkMessageRecipientUpdateVO extends FriendtalkMessageRecipientVO implements Serializable {
    private static final long serialVersionUID = 1L;

}

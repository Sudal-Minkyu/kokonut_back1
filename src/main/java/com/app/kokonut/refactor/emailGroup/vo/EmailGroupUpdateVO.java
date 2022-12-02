package com.app.kokonut.refactor.emailGroup.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel("Update ")
@EqualsAndHashCode(callSuper = false)
public class EmailGroupUpdateVO extends EmailGroupVO implements Serializable {
    private static final long serialVersionUID = 1L;

}

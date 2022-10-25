package com.app.kokonut.admin.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel("Update ")
@EqualsAndHashCode(callSuper = false)
public class AdminUpdateVO extends AdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

}

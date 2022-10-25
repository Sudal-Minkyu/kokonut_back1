package com.app.kokonut.adminLevel.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel("Update ")
@EqualsAndHashCode(callSuper = false)
public class AdminLevelUpdateVO extends AdminLevelVO implements Serializable {
    private static final long serialVersionUID = 1L;

}

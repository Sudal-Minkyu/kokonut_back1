package com.app.kokonut.refactor.setting.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel("Update ")
@EqualsAndHashCode(callSuper = false)
public class SettingUpdateVO extends SettingVO implements Serializable {
    private static final long serialVersionUID = 1L;

}

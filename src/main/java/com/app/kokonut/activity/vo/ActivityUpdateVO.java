package com.app.kokonut.activity.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@io.swagger.annotations.ApiModel("Update ")
@EqualsAndHashCode(callSuper = false)
public class ActivityUpdateVO extends ActivityVO implements Serializable {
    private static final long serialVersionUID = 1L;

}

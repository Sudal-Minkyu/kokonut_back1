package com.app.kokonut.collectInformation.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel("Update ")
@EqualsAndHashCode(callSuper = false)
public class CollectInformationUpdateVO extends CollectInformationVO implements Serializable {
    private static final long serialVersionUID = 1L;

}

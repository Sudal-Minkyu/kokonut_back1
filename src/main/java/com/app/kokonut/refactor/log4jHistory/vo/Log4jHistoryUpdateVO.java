package com.app.kokonut.refactor.log4jHistory.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel("Update ")
@EqualsAndHashCode(callSuper = false)
public class Log4jHistoryUpdateVO extends Log4jHistoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

}

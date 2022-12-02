package com.app.kokonut.refactor.shedlock.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel("Update 스케줄잠금")
@EqualsAndHashCode(callSuper = false)
public class ShedlockUpdateVO extends ShedlockVO implements Serializable {
    private static final long serialVersionUID = 1L;

}

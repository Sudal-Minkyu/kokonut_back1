package com.app.kokonut.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeStateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("0:게시중지,1:게시중,2:게시대기")
    private Integer state;

}

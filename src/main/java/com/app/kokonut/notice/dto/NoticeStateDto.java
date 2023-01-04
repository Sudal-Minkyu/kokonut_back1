package com.app.kokonut.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * @author Joy
 * Date : 2022-01-02
 * Time :
 * Remark : 공지사항 상태 변경 시 사용하는 DTO
 */
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

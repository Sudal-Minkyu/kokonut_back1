package com.app.kokonut.refactor.setting.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("Save ")
public class SettingVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @NotNull(message = "idx can not null")
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 회사(Company) 키
     */
    @ApiModelProperty("회사(Company) 키")
    private Integer companyIdx;


    /**
     * 해외로그인차단(0:차단안함,1:차단)
     */
    @ApiModelProperty("해외로그인차단(0:차단안함,1:차단)")
    private Integer overseasBlock;


    /**
     * 휴면회원 전환 시(0:다른DB로 정보이관
     , 1:이관 없이 회원정보 삭제)
     */
    @ApiModelProperty("휴면회원 전환 시(0:다른DB로 정보이관, 1:이관 없이 회원정보 삭제)")
    private Integer dormantAccount;


    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    private Date regdate;


    /**
     * 수정일시
     */
    @ApiModelProperty("수정일시")
    private Date modifyDate;

}
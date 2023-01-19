package com.app.kokonut.setting.dtos;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Joy
 * Date : 2023-01-05
 * Time :
 * Remark : SettingDto 관리자 환경설정 기본 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnSettingDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("회사(Company) 키")
    private Integer companyIdx;

    @ApiModelProperty("해외로그인차단(0:차단안함,1:차단)")
    private Integer overseasBlock;

    @ApiModelProperty("휴면회원 전환 시(0:다른DB로 정보이관, 1:이관 없이 회원정보 삭제)")
    private Integer dormantAccount;

    @ApiModelProperty("등록일시")
    private LocalDateTime regdate;

    @ApiModelProperty("수정일시")
    private LocalDateTime modifyDate;

}

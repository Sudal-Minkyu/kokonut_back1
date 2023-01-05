package com.app.kokonutuser.common.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Woody
 * Date : 2022-12-27
 * Time :
 * Remark : 유저테이블 생성을 위해 불러오는 Default테이블 필드조회 Dto
 */
@Data
@NoArgsConstructor
public class CommonFieldDto {

    private String tableField;

    private String tableType;

    private String tableCollation;

    private String tableNull;

    private String tableKey;

    private String tableDefault;

    private String tableExtra;

    private String tablePrivileges;

    private String tableComment;

}

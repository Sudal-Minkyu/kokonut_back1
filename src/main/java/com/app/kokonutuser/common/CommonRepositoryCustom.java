package com.app.kokonutuser.common;

import com.app.kokonutuser.common.dto.CommonFieldDto;

import java.util.List;

public interface CommonRepositoryCustom {

    List<CommonFieldDto> selectCommonUserTable();

}

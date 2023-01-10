package com.app.kokonutdormant;

import com.app.kokonutdormant.dtos.KokonutDormantListDto;
import com.app.kokonutuser.dtos.KokonutRemoveInfoDto;
import com.app.kokonutuser.dtos.KokonutUserFieldDto;

import java.util.List;

public interface DynamicDormantRepositoryCustom {

    void dormantCommonTable(String commonQuery); // 생성, 삭제, 업데이트 실행 공용

    List<KokonutDormantListDto> findByDormantPage(String searchQuery);

    Integer selectDormantCount(String searchQuery);

    List<KokonutRemoveInfoDto> selectDormantDataByIdx(String searchQuery);

    Integer selectDormantIdCheck(String searchQuery); // 아이디 존재 유무 확인

    List<KokonutUserFieldDto> selectDormantColumns(String searchQuery);

}

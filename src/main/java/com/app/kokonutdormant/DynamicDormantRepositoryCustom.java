package com.app.kokonutdormant;

import com.app.kokonutdormant.dtos.KokonutDormantListDto;

import java.util.List;

public interface DynamicDormantRepositoryCustom {

    List<KokonutDormantListDto> findByDormantPage(String searchQuery);
}

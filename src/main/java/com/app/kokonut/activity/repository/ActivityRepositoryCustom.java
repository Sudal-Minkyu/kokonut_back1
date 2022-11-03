package com.app.kokonut.activity.repository;

import com.app.kokonut.activity.dto.ActivityDto;
import com.app.kokonut.apiKey.dtos.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.dtos.ApiKeyMapperDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Woody
 * Date : 2022-11-03
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 Activity Sql 쿼리호출
 */
@Repository
public interface ActivityRepositoryCustom {

    // Activity 활동 리스트 조회
    List<ActivityDto> findByActivityTypeList(Integer type); // SelectActivityList -> 변경후

}
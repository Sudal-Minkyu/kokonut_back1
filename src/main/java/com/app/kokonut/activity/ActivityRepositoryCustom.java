package com.app.kokonut.activity;

import com.app.kokonut.activity.dto.ActivityListDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Woody
 * Date : 2022-11-03
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 Activity Sql 쿼리호출
 */
public interface ActivityRepositoryCustom {

    List<ActivityListDto> findByActivityTypeList(Integer type); // Activity 활동 리스트 조회 SelectActivityList -> 변경후

}
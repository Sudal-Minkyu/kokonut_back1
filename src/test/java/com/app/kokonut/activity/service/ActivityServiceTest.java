package com.app.kokonut.activity.service;

import com.app.kokonut.activity.ActivityService;
import com.app.kokonut.activity.dto.ActivityListDto;
import com.app.kokonut.activity.Activity;
import com.app.kokonut.activity.ActivityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    @DisplayName("활동 리스트 조회 테스트 : param type 값이 4 일 경우 테스트 ")
    public void findByActivityTypeListTest1(){
        // when
        List<ActivityListDto> activityListDtoList = activityService.findByActivityTypeList(4);
        System.out.println("activityDtoList : "+ activityListDtoList);
    }

    @Test
    @DisplayName("활동 리스트 조회 테스트 : param type 값이 1 일 경우 테스트 ")
    public void findByActivityTypeListTest2(){
        // when
        List<ActivityListDto> activityListDtoList = activityService.findByActivityTypeList(1);
        System.out.println("activityDtoList : "+ activityListDtoList);
    }

    @Test
    @DisplayName("Activity 활동 리스트 저장 및 삭제 테스트 - " +
            "1. Activity 리스트 값을 saveActivityList호출 하여 성공적으로 저장한다. " +
            "2. 해당 리스트 데이터를 삭제한 후 다시 조회하여 테스트를 마무리한다.")
    public void saveActivityListTest(){

        // given
        List<Activity> activities = new ArrayList<>();

        // 10개 데이터를 일괄저장
        for(int i=0; i<10; i++){
            Activity activity = new Activity();
            activity.setType(99);
            activity.setRegdate(new Date());
            activities.add(activity);
        }

        // when
        List<Activity> activityList = activityService.saveActivityList(activities);
        System.out.println("인서트 완료 activityList : "+activityList);

        List<ActivityListDto> activityListDtoList = activityService.findByActivityTypeList(99); // type 99로 조회
        System.out.println("activityDtoList : "+ activityListDtoList);

        assertEquals(10, activityListDtoList.size()); // 인서트한 값이 10개인지 확인

        activityRepository.deleteAll(activityList);

        List<ActivityListDto> activityListDtoListCheck = activityService.findByActivityTypeList(99); // 다시 type 99로 조회

        assertEquals(0, activityListDtoListCheck.size()); // 모두 삭제됬는지 확인 : 인서트한 값이 0개인지 확인

        System.out.println("saveActivityListTest : 테스트 성공");
    }



}
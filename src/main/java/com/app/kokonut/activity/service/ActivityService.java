package com.app.kokonut.activity.service;

import com.app.kokonut.activity.dto.ActivityDto;
import com.app.kokonut.activity.entity.Activity;
import com.app.kokonut.activity.repository.ActivityRepository;
import com.app.kokonut.apiKey.entity.ApiKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /***
     * 활동 리스트 조회
     * @param Map [type - 활동 종류(1:고객정보처리,2:관리자활동)]
     * @return List
     */
//    public List<HashMap<String, Object>> SelectActivityList(Map<String, Object> paramMap){
//        return dao.SelectActivityList(paramMap);
//    }
    public List<ActivityDto> findByActivityTypeList(Integer type) {
        log.info("findByActivityTypeList 호출");
        return activityRepository.findByActivityTypeList(type);
    }

    /***
     * 활동 업데이트
     * @param paramMap
     * --> 사용하지 않은듯(?)
     */
//    public void UpdateActivity(HashMap<String, Object> paramMap){
//        dao.UpdateActivity(paramMap);
//    }

    /***
     * 활동 리스트 저장
     * @param activityList = 저장할 활동 리스트
     */
//    public void SaveActivityList(List<HashMap<String, Object>> activityList) {
//        for (int i = 0; i < activityList.size(); i++) {
//            dao.UpdateActivity(activityList.get(i));
//        }
//    }
    /** JPA saveAll()로 재구성 : SaveActivityList -> 변경후
     * 리스트형태로 받은 Activity들을 모두 저장(업데이트)
     * param
     * - List<Activity> activityList
     */
    @Transactional
    public List<Activity> saveActivityList(List<Activity> activityList) {
        log.info("saveActivityList 호출");
        return activityRepository.saveAll(activityList);
    }


}

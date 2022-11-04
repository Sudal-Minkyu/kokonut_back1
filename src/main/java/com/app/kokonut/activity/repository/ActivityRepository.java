package com.app.kokonut.activity.repository;

import com.app.kokonut.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity>, ActivityRepositoryCustom {

//    List<HashMap<String, Object>> SelectActivityList(Map<String, Object> paramMap); // 변경전 - RepositoryCustom 완료 @@@@

//    void UpdateActivity(HashMap<String, Object> paramMap); // 변경전 - Service 완료 @@@@

}
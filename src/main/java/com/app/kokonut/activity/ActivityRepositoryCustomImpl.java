//package com.app.kokonut.activity;
//
//import com.app.kokonut.activity.dto.ActivityListDto;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.JPQLQuery;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * @author Woody
// * Date : 2022-11-03
// * Time :
// * Remark : ActivityRepositoryCustom 쿼리문 선언부
// */
//@Repository
//public class ActivityRepositoryCustomImpl extends QuerydslRepositorySupport implements ActivityRepositoryCustom {
//
//    public ActivityRepositoryCustomImpl() {
//        super(Activity.class);
//    }
//
//    // Activity 리스트 조회
//    @Override
//    public List<ActivityListDto> findByActivityTypeList(Integer type) {
//
//        QActivity activity = QActivity.activity;
//
//        JPQLQuery<ActivityListDto> query = from(activity)
//                .select(Projections.constructor(ActivityListDto.class,
//                        activity.idx,
//                        activity.isActivity,
//                        activity.month,
//                        activity.regdate,
//                        activity.modifyDate
//
//                ));
//
//        if(type != null){
//            // 프론트에게 받아온 param의 type 데이터 값이 4일 경우 type 2와 3을 모두 조회.
//            // 그 외엔 받아온 타입의 따라 조회한다.
//            if(type == 4){
//                query.where(activity.type.in(2,3));
//            }else{
//                query.where(activity.type.eq(type));
//            }
//        }
//
//        return query.fetch();
//    }
//
//}

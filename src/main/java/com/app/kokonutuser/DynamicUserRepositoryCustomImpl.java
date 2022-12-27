package com.app.kokonutuser;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * @author Woody
 * Date : 2022-12-27
 * Time :
 * Remark : Kokonut-user DB & 테이블 - 쿼리문 선언부 모두 NativeQuery로 실행한다.
 */
public class DynamicUserRepositoryCustomImpl extends QuerydslRepositorySupport implements DynamicUserRepositoryCustom {

    @Autowired
    JpaResultMapper jpaResultMapper;

    public DynamicUserRepositoryCustomImpl(Class<?> domainClass) {
        super(domainClass);
    }




}

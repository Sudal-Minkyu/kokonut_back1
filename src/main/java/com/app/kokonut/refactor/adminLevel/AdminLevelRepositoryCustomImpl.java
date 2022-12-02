package com.app.kokonut.refactor.adminLevel;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Woody
 * Date : 2022-11-29
 * Time :
 * Remark : AdminLevelRepositoryCustom 쿼리문 선언부
 */
@Repository
public class AdminLevelRepositoryCustomImpl extends QuerydslRepositorySupport implements AdminLevelRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public AdminLevelRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(AdminLevel.class);
        this.jpaResultMapper = jpaResultMapper;
    }

}

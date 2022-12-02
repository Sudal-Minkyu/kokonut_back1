package com.app.kokonut.admin;

import com.app.kokonut.admin.entity.Admin;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Woody
 * Date : 2022-11-29
 * Time :
 * Remark : AdminRepositoryCustom 쿼리문 선언부
 */
@Repository
public class AdminRepositoryCustomImpl extends QuerydslRepositorySupport implements AdminRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public AdminRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(Admin.class);
        this.jpaResultMapper = jpaResultMapper;
    }

}

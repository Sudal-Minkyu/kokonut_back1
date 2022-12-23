package com.app.kokonut.company;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Woody
 * Date : 2022-12-22
 * Time :
 * Remark : CompanyRepositoryCustom 쿼리문 선언부
 */
@Repository
public class CompanyRepositoryCustomImpl extends QuerydslRepositorySupport implements CompanyRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public CompanyRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(Company.class);
        this.jpaResultMapper = jpaResultMapper;
    }

}

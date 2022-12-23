package com.app.kokonut.company;

import com.app.kokonut.admin.AdminRepositoryCustom;
import com.app.kokonut.admin.dtos.AdminCompanyInfoDto;
import com.app.kokonut.admin.dtos.AdminEmailInfoDto;
import com.app.kokonut.admin.dtos.AdminOtpKeyDto;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.admin.entity.QAdmin;
import com.app.kokonut.company.entity.QCompany;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
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

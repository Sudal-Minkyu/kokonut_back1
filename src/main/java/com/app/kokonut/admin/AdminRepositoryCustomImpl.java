package com.app.kokonut.admin;

import com.app.kokonut.admin.dtos.AdminCompanyInfoDto;
import com.app.kokonut.admin.dtos.AdminEmailInfoDto;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.admin.entity.QAdmin;
import com.app.kokonut.admin.dtos.AdminOtpKeyDto;
import com.app.kokonut.admin.entity.enums.AuthorityRole;
import com.app.kokonut.company.QCompany;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    // Admin OtpKey 단일 조회
    @Override
    public AdminOtpKeyDto findByOtpKey(String email) {
        QAdmin admin = QAdmin.admin;

        JPQLQuery<AdminOtpKeyDto> query = from(admin)
                .where(admin.email.eq(email))
                .select(Projections.constructor(AdminOtpKeyDto.class,
                        admin.otpKey
                ));

        return query.fetchOne();
    }

    // Admin 및 Company 정보 단일조회
    @Override
    public AdminCompanyInfoDto findByCompanyInfo(String email) {

        QAdmin admin = QAdmin.admin;
        QCompany company = QCompany.company;

        JPQLQuery<AdminCompanyInfoDto> query = from(admin)
                .innerJoin(company).on(company.idx.eq(admin.companyIdx))
                .where(admin.email.eq(email))
                .select(Projections.constructor(AdminCompanyInfoDto.class,
                        admin.idx,
                        company.idx,
                        company.businessNumber
                ));

        return query.fetchOne();
    }

    @Override
    public AdminEmailInfoDto findByEmailInfo(Integer idx) {
        QAdmin admin = QAdmin.admin;

        JPQLQuery<AdminEmailInfoDto> query = from(admin)
                .where(admin.idx.eq(idx))
                .select(Projections.constructor(AdminEmailInfoDto.class,
                        admin.email,
                        admin.name
                ));
        return query.fetchOne();
    }

    // 시스템관리자 목록 (이름, 이메일) 조회
    @Override
    public List<AdminEmailInfoDto> findSystemAdminEmailInfo() {
        QAdmin admin = QAdmin.admin;
        JPQLQuery<AdminEmailInfoDto> query = from(admin)
                .where(admin.state.eq(1), admin.roleName.eq(AuthorityRole.ROLE_SYSTEM))
                .select(Projections.constructor(AdminEmailInfoDto.class,
                        admin.email,
                        admin.name
                ));
        return query.fetch();
    }

}

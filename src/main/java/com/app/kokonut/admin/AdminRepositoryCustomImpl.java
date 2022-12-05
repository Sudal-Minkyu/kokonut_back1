package com.app.kokonut.admin;

import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.admin.entity.QAdmin;
import com.app.kokonut.admin.dtos.AdminOtpKeyDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
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

}

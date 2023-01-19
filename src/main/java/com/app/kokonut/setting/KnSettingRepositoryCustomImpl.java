package com.app.kokonut.setting;

import com.app.kokonut.company.QCompany;
import com.app.kokonut.setting.entity.QSetting;
import com.app.kokonut.setting.dtos.KnSettingDetailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author joy
 * Date : 2023-01-05
 * Time :
 * Remark : SettingRepositoryCustom 쿼리문 선언부
 */
@Repository
public class KnSettingRepositoryCustomImpl extends QuerydslRepositorySupport implements KnSettingRepositoryCustom {

    public KnSettingRepositoryCustomImpl() {
        super(KnSetting.class);
    }

    @Override
    public KnSettingDetailDto findSettingDetailByCompanyIdx(Integer companyIdx) {

        /*
         *  SELECT A.IDX
         *       , A.OVERSEAS_BLOCK
         *       , A.DORMANT_ACCOUNT
         *    FROM `setting` AS A
         *    JOIN `company` AS B
         *      ON A.COMPANY_IDX = B.IDX
         *   WHERE A.COMPANY_IDX = #{companyIdx}
         */

        QSetting setting = QSetting.setting;
        QCompany company = QCompany.company;

        JPQLQuery<KnSettingDetailDto> query = from(setting)
                .where(setting.companyIdx.eq(companyIdx))
                .join(company).on(setting.companyIdx.eq(company.idx))
                .select(Projections.constructor(KnSettingDetailDto.class,
                        setting.idx,
                        setting.overseasBlock,
                        setting.dormantAccount));
        return query.fetchOne();
    }
}

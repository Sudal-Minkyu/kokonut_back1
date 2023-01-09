package com.app.kokonut.setting;

import com.app.kokonut.company.CompanyRepository;
import com.app.kokonut.company.QCompany;
import com.app.kokonut.setting.entity.QSetting;
import com.app.kokonut.setting.dto.SettingDetailDto;
import com.app.kokonut.setting.entity.Setting;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author joy
 * Date : 2023-01-05
 * Time :
 * Remark : SettingRepositoryCustom 쿼리문 선언부
 */
@Repository
public class SettingRepositoryCustomImpl extends QuerydslRepositorySupport implements SettingRepositoryCustom {

    public SettingRepositoryCustomImpl() {
        super(Setting.class);
    }

    @Override
    public SettingDetailDto findSettingDetailByCompanyIdx(Integer companyIdx) {

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

        JPQLQuery<SettingDetailDto> query = from(setting)
                .where(setting.companyIdx.eq(companyIdx))
                .join(company).on(setting.companyIdx.eq(company.idx))
                .select(Projections.constructor(SettingDetailDto.class,
                        setting.idx,
                        setting.overseasBlock,
                        setting.dormantAccount));
        return query.fetchOne();
    }
}

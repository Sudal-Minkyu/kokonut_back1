package com.app.kokonut.setting;

import com.app.kokonut.setting.entity.Setting;
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
    public final JpaResultMapper jpaResultMapper;

    public SettingRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(Setting.class);
        this.jpaResultMapper = jpaResultMapper;
    }

}

package com.app.kokonut.collectInformation;

import com.app.kokonut.collectInformation.entity.CollectInformation;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : CollectInformationRepository 개인정보 처리방침 쿼리 선언부
 */
@Repository
public class CollectInformationRepositoryCustomImpl extends QuerydslRepositorySupport implements CollectInformationRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;

    public CollectInformationRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(CollectInformation.class);
        this.jpaResultMapper = jpaResultMapper;
    }
}

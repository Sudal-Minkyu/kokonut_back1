package com.app.kokonut.refactor.alimtalkTemplate;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Woody
 * Date : 2022-11-29
 * Time :
 * Remark : AlimtalkTemplateRepositoryCustomImpl 쿼리문 선언부
 */
@Repository
public class AlimtalkTemplateRepositoryCustomImpl extends QuerydslRepositorySupport implements AlimtalkTemplateRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public AlimtalkTemplateRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(AlimtalkTemplate.class);
        this.jpaResultMapper = jpaResultMapper;
    }

}

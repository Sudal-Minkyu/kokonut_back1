package com.app.kokonut.faq;

import com.app.kokonut.faq.entity.Faq;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * @author joy
 * Date : 2022-12-27
 * Time :
 * Remark : FaqRepositoryCustom 쿼리문 선언부
 */
public class FaqRepositoryCustomImpl extends QuerydslRepositorySupport implements FaqRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;

    public FaqRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(Faq.class);
        this.jpaResultMapper = jpaResultMapper;
    }
}

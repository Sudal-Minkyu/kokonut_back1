package com.app.kokonut.qna;

import com.app.kokonut.qna.entity.Qna;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
/**
 * @author joy
 * Date : 2022-12-27
 * Time :
 * Remark : QnaRepositoryCustom 쿼리문 선언부
 */
public class QnaRepositoryCustomImpl extends QuerydslRepositorySupport implements QnaRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;

    public QnaRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(Qna.class);
        this.jpaResultMapper = jpaResultMapper;
    }
}

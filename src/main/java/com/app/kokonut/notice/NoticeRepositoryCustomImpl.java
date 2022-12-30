package com.app.kokonut.notice;

import com.app.kokonut.notice.entity.Notice;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * @author joy
 * Date : 2022-12-27
 * Time :
 * Remark : NoticeRepositoryCustom 쿼리문 선언부
 */
public class NoticeRepositoryCustomImpl extends QuerydslRepositorySupport implements NoticeRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;

    public NoticeRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(Notice.class);
        this.jpaResultMapper = jpaResultMapper;
    }
}

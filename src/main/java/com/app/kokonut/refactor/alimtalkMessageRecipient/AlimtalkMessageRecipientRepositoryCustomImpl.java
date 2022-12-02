package com.app.kokonut.refactor.alimtalkMessageRecipient;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Woody
 * Date : 2022-11-29
 * Time :
 * Remark : AlimtalkMessageRecipientRepositoryCustom 쿼리문 선언부
 */
@Repository
public class AlimtalkMessageRecipientRepositoryCustomImpl extends QuerydslRepositorySupport implements AlimtalkMessageRecipientRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public AlimtalkMessageRecipientRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(AlimtalkMessageRecipient.class);
        this.jpaResultMapper = jpaResultMapper;
    }

}

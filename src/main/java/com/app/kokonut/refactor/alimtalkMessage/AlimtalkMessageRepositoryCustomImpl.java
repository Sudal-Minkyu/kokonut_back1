package com.app.kokonut.refactor.alimtalkMessage;

import com.app.kokonut.refactor.adminRemove.AdminRemoveRepositoryCustom;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Woody
 * Date : 2022-11-29
 * Time :
 * Remark : AlimtalkMessageRepositoryCustom 쿼리문 선언부
 */
@Repository
public class AlimtalkMessageRepositoryCustomImpl extends QuerydslRepositorySupport implements AlimtalkMessageRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public AlimtalkMessageRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(AlimtalkMessage.class);
        this.jpaResultMapper = jpaResultMapper;
    }

}

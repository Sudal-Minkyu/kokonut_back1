package com.app.kokonut.downloadHistory;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Woody
 * Date : 2023-01-13
 * Time :
 * Remark : DownloadHistoryRepositoryCustom 쿼리문 선언부
 */
@Repository
public class DownloadHistoryRepositoryCustomImpl extends QuerydslRepositorySupport implements DownloadHistoryRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public DownloadHistoryRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(DownloadHistory.class);
        this.jpaResultMapper = jpaResultMapper;
    }


}

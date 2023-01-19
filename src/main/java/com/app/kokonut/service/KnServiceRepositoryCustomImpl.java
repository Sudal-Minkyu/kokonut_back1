package com.app.kokonut.service;

import com.app.kokonut.service.dtos.KnServiceDto;
import com.app.kokonut.service.entity.QService;
import com.app.kokonut.setting.KnSetting;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @author joy
 * Date : 2023-01-05
 * Time :
 * Remark : SettingRepositoryCustom 쿼리문 선언부
 */
@Repository
public class KnServiceRepositoryCustomImpl extends QuerydslRepositorySupport implements KnServiceRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;

    public KnServiceRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(KnSetting.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    @Override
    public List<KnServiceDto> findServiceList() {
         /*
           SELECT `IDX`
                , `SERVICE`
	            , `PRICE`
	            , `PER_PRICE`
             FROM `service`
            WHERE 1 = 1
         */
        QService service = QService.service;
        JPQLQuery<KnServiceDto> query = from(service)
                .select(Projections.constructor(KnServiceDto.class,
                        service.idx,
                        service.ksService,
                        service.ksPrice,
                        service.ksPerPrice
                ));
        final List<KnServiceDto> knServiceDtos = Objects.requireNonNull(query.fetchAll()).fetch();
        return knServiceDtos;
    }

    @Override
    public KnServiceDto findServiceByIdx(Integer idx) {
        /*
           SELECT `IDX`
                , `SERVICE`
	            , `PRICE`
	            , `PER_PRICE`
             FROM `service`
            WHERE 1 = 1
              AND `IDX` = #{idx}
         */
        QService service = QService.service;
        JPQLQuery<KnServiceDto> query = from(service)
                .select(Projections.constructor(KnServiceDto.class,
                        service.idx,
                        service.ksService,
                        service.ksPrice,
                        service.ksPerPrice
                ));
        query.where(service.idx.eq(idx));
        return query.fetchOne();
    }
}

package com.app.kokonut.service;

import com.app.kokonut.service.dto.ServiceDto;
import com.app.kokonut.service.entity.QService;
import com.app.kokonut.setting.entity.Setting;
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
public class ServiceRepositoryCustomImpl extends QuerydslRepositorySupport implements ServiceRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;

    public ServiceRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(Setting.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    @Override
    public List<ServiceDto> findServiceList() {
         /*
           SELECT `IDX`
                , `SERVICE`
	            , `PRICE`
	            , `PER_PRICE`
             FROM `service`
            WHERE 1 = 1
         */
        QService service = QService.service;
        JPQLQuery<ServiceDto> query = from(service)
                .select(Projections.constructor(ServiceDto.class,
                        service.idx,
                        service.ksService,
                        service.ksPrice,
                        service.ksPerPrice
                ));
        final List<ServiceDto> serviceDtos = Objects.requireNonNull(query.fetchAll()).fetch();
        return serviceDtos;
    }

    @Override
    public ServiceDto findServiceByIdx(Integer idx) {
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
        JPQLQuery<ServiceDto> query = from(service)
                .select(Projections.constructor(ServiceDto.class,
                        service.idx,
                        service.ksService,
                        service.ksPrice,
                        service.ksPerPrice
                ));
        query.where(service.idx.eq(idx));
        return query.fetchOne();
    }
}

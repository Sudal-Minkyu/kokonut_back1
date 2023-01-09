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
    private final ServiceRepository serviceRepository;
    public final JpaResultMapper jpaResultMapper;

    public ServiceRepositoryCustomImpl(JpaResultMapper jpaResultMapper,
                                       ServiceRepository serviceRepository) {
        super(Setting.class);
        this.jpaResultMapper = jpaResultMapper;
        this.serviceRepository = serviceRepository;
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
                        service.isService,
                        service.price,
                        service.perPrice
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
                        service.isService,
                        service.price,
                        service.perPrice
                ));
        query.where(service.idx.eq(idx));
        return query.fetchOne();
    }
}

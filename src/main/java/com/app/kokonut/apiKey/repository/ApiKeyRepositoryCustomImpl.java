package com.app.kokonut.apiKey.repository;

import com.app.kokonut.admin.entity.QAdmin;
import com.app.kokonut.apiKey.dtos.ApiKeyMapperDto;
import com.app.kokonut.apiKey.dtos.ApiKeyKeyDto;
import com.app.kokonut.apiKey.dtos.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.dtos.TestApiKeyExpiredListDto;
import com.app.kokonut.apiKey.entity.ApiKey;
import com.app.kokonut.apiKey.entity.QApiKey;
import com.app.kokonut.company.entity.QCompany;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark : ApiKeyRepositoryCustom 쿼리문 선언부
 */
@Slf4j
@Repository
public class ApiKeyRepositoryCustomImpl extends QuerydslRepositorySupport implements ApiKeyRepositoryCustom {

    @Autowired
    JpaResultMapper jpaResultMapper;

    public ApiKeyRepositoryCustomImpl() {
        super(ApiKey.class);
    }

    // ApiKey 리스트 조회
    @Override
    public List<ApiKeyListAndDetailDto> findByApiKeyList(ApiKeyMapperDto apiKeyMapperDto) {

        QApiKey apiKey = QApiKey.apiKey;
        QAdmin admin = QAdmin.admin;
        QCompany company = QCompany.company;

        Date systemDate = new Date(System.currentTimeMillis());

        JPQLQuery<ApiKeyListAndDetailDto> query = from(apiKey)
                .innerJoin(admin).on(admin.idx.eq(apiKey.adminIdx))
                .innerJoin(company).on(company.idx.eq(admin.companyIdx))
                .select(Projections.constructor(ApiKeyListAndDetailDto.class,
                        apiKey.idx,
                        apiKey.companyIdx,
                        apiKey.adminIdx,
                        apiKey.key,
                        apiKey.regdate,

                        apiKey.type,
                        apiKey.note,
                        apiKey.validityStart,
                        apiKey.validityEnd,

                        new CaseBuilder()
                                .when(apiKey.validityStart.loe(systemDate).and(apiKey.validityEnd.goe(systemDate))).then("Y")
                                .otherwise("N"),

                        apiKey.useAccumulate,
                        apiKey.state,
                        apiKey.useYn,

                        apiKey.reason,
                        apiKey.modifierIdx,
                        apiKey.modifierName,
                        apiKey.modifyDate,

                        admin.name,
                        company.companyName
                ));

        if(!apiKeyMapperDto.getUseYn().equals("") && apiKeyMapperDto.getUseYn() != null){
            query.where(apiKey.useYn.eq(String.valueOf(apiKeyMapperDto.getUseYn())));
        }

        if(apiKeyMapperDto.getType() != null){
            query.where(apiKey.type.eq(apiKeyMapperDto.getType()));
        }

        if(!apiKeyMapperDto.getBeInUse().equals("") && apiKeyMapperDto.getBeInUse() != null){
            if(apiKeyMapperDto.getBeInUse().equals("Y")){
                query.where(apiKey.validityStart.gt(systemDate).or(apiKey.validityEnd.lt(systemDate)));
            }else{
                query.where(apiKey.validityEnd.lt(systemDate));
            }
        }

        if(apiKeyMapperDto.getStimeStart() != null && apiKeyMapperDto.getStimeEnd() != null){
            query.where(apiKey.regdate.between(apiKeyMapperDto.getStimeStart(), apiKeyMapperDto.getStimeEnd()));
        }

        if(!apiKeyMapperDto.getSearchText().equals("") && apiKeyMapperDto.getSearchText() != null){
            query.where(apiKey.key.like("%"+ apiKeyMapperDto.getSearchText() +"%"));
        }

        return query.fetch();
    }

    // ApiKey 리스트 Count 조회
    @Override
    public Long findByApiKeyListCount(ApiKeyMapperDto apiKeyMapperDto) {

        QApiKey apiKey = QApiKey.apiKey;
        QAdmin admin = QAdmin.admin;
        QCompany company = QCompany.company;

        Date systemDate = new Date(System.currentTimeMillis());

        JPQLQuery<Long> query = from(apiKey)
                .innerJoin(admin).on(admin.idx.eq(apiKey.adminIdx))
                .innerJoin(company).on(company.idx.eq(admin.companyIdx))
                .select(Projections.constructor(Long.class,
                        apiKey.count()
                ));

        if(!apiKeyMapperDto.getUseYn().equals("") && apiKeyMapperDto.getUseYn() != null){
            query.where(apiKey.useYn.eq(String.valueOf(apiKeyMapperDto.getUseYn())));
        }

        if(apiKeyMapperDto.getType() != null){
            query.where(apiKey.type.eq(apiKeyMapperDto.getType()));
        }

        if(!apiKeyMapperDto.getBeInUse().equals("") && apiKeyMapperDto.getBeInUse() != null){
            if(apiKeyMapperDto.getBeInUse().equals("Y")){
                query.where(apiKey.validityStart.gt(systemDate).or(apiKey.validityEnd.lt(systemDate)));
            }else{
                query.where(apiKey.validityEnd.lt(systemDate));
            }
        }

        if(apiKeyMapperDto.getStimeStart() != null && apiKeyMapperDto.getStimeEnd() != null){
            query.where(apiKey.regdate.between(apiKeyMapperDto.getStimeStart(), apiKeyMapperDto.getStimeEnd()));
        }

        if(!apiKeyMapperDto.getSearchText().equals("") && apiKeyMapperDto.getSearchText() != null){
            query.where(apiKey.key.like("%"+ apiKeyMapperDto.getSearchText() +"%"));
        }

        return query.fetchOne();
    }

    // ApiKey 상세보기
    @Override
    public ApiKeyListAndDetailDto findByApiKeyDetail(Integer idx) {

        QApiKey apiKey = QApiKey.apiKey;
        QAdmin admin = QAdmin.admin;
        QCompany company = QCompany.company;

        Date systemDate = new Date(System.currentTimeMillis());

        JPQLQuery<ApiKeyListAndDetailDto> query = from(apiKey)
                .innerJoin(admin).on(admin.idx.eq(apiKey.adminIdx))
                .innerJoin(company).on(company.idx.eq(admin.companyIdx))
                .where(apiKey.idx.eq(idx))
                .select(Projections.constructor(ApiKeyListAndDetailDto.class,
                        apiKey.idx,
                        apiKey.companyIdx,
                        apiKey.adminIdx,
                        apiKey.key,
                        apiKey.regdate,

                        apiKey.type,
                        apiKey.note,
                        apiKey.validityStart,
                        apiKey.validityEnd,

                        new CaseBuilder()
                                .when(apiKey.validityStart.loe(systemDate).and(apiKey.validityEnd.goe(systemDate))).then("Y")
                                .otherwise("N"),

                        apiKey.useAccumulate,
                        apiKey.state,
                        apiKey.useYn,

                        apiKey.reason,
                        apiKey.modifierIdx,
                        apiKey.modifierName,
                        apiKey.modifyDate,

                        admin.name,
                        company.companyName
                ));

        return query.fetchOne();
    }

    // ApiKey 단일 조회
    @Override
    public ApiKeyKeyDto findByKey(String key) {
        QApiKey apiKey = QApiKey.apiKey;

        JPQLQuery<ApiKeyKeyDto> query = from(apiKey)
                .where(apiKey.key.eq(key))
                .select(Projections.constructor(ApiKeyKeyDto.class,
                        apiKey.idx,
                        apiKey.companyIdx,
                        apiKey.adminIdx,
                        apiKey.registerName,
                        apiKey.key,
                        apiKey.regdate,
                        apiKey.type,
                        apiKey.note,

                        apiKey.validityStart,
                        apiKey.validityEnd,

                        apiKey.useAccumulate,
                        apiKey.state,
                        apiKey.useYn,

                        apiKey.reason,
                        apiKey.modifierIdx,
                        apiKey.modifierName,
                        apiKey.modifyDate
                ));

        return query.fetchOne();
    }

    // Test Api Key 조회 : param -> companyIdx
    @Override
    public ApiKeyListAndDetailDto findByTestApiKeyByCompanyIdx(Integer companyIdx, Integer type) {

        QApiKey apiKey = QApiKey.apiKey;
        QAdmin admin = QAdmin.admin;
        QCompany company = QCompany.company;

        Date systemDate = new Date(System.currentTimeMillis());

        JPQLQuery<ApiKeyListAndDetailDto> query = from(apiKey)
                .innerJoin(admin).on(admin.idx.eq(apiKey.adminIdx))
                .innerJoin(company).on(company.idx.eq(admin.companyIdx))
                .where(apiKey.companyIdx.eq(companyIdx).and(apiKey.type.eq(type)))
                .select(Projections.constructor(ApiKeyListAndDetailDto.class,
                        apiKey.idx,
                        apiKey.companyIdx,
                        apiKey.adminIdx,
                        apiKey.key,
                        apiKey.regdate,

                        apiKey.type,
                        apiKey.note,
                        apiKey.validityStart,
                        apiKey.validityEnd,

                        new CaseBuilder()
                                .when(apiKey.validityStart.loe(systemDate).and(apiKey.validityEnd.goe(systemDate))).then("Y")
                                .otherwise("N"),

                        apiKey.useAccumulate,
                        apiKey.state,
                        apiKey.useYn,

                        apiKey.reason,
                        apiKey.modifierIdx,
                        apiKey.modifierName,
                        apiKey.modifyDate,

                        admin.name,
                        company.companyName
                ));

        return query.fetchOne();
    }

    // TestApiKey 중복 조회 : param -> key, type = 2
    @Override
    public Long findByTestApiKeyDuplicateCount(String key, Integer type) {

        QApiKey apiKey = QApiKey.apiKey;

        JPQLQuery<Long> query = from(apiKey)
                .where(apiKey.key.eq(key).and(apiKey.type.eq(type)))
                .select(Projections.constructor(Long.class,
                        apiKey.count()
                ));

        return query.fetchOne();
    }

    // ApiKey 단일 조회 : param -> companyIdx, type = 1, useYn = "Y"
    @Override
    public ApiKeyListAndDetailDto findByApiKeyByCompanyIdx(Integer companyIdx, Integer type, String useYn) {

        QApiKey apiKey = QApiKey.apiKey;
        QAdmin admin = QAdmin.admin;
        QCompany company = QCompany.company;

        Date systemDate = new Date(System.currentTimeMillis());

        JPQLQuery<ApiKeyListAndDetailDto> query = from(apiKey)
                .innerJoin(admin).on(admin.idx.eq(apiKey.adminIdx))
                .innerJoin(company).on(company.idx.eq(admin.companyIdx))
                .where(apiKey.companyIdx.eq(companyIdx).and(apiKey.type.eq(type).and(apiKey.useYn.eq(useYn))))
                .select(Projections.constructor(ApiKeyListAndDetailDto.class,
                        apiKey.idx,
                        apiKey.companyIdx,
                        apiKey.adminIdx,
                        apiKey.key,
                        apiKey.regdate,

                        apiKey.type,
                        apiKey.note,
                        apiKey.validityStart,
                        apiKey.validityEnd,

                        new CaseBuilder()
                                .when(apiKey.validityStart.loe(systemDate).and(apiKey.validityEnd.goe(systemDate))).then("Y")
                                .otherwise("N"),

                        apiKey.useAccumulate,
                        apiKey.state,
                        apiKey.useYn,

                        apiKey.reason,
                        apiKey.modifierIdx,
                        apiKey.modifierName,
                        apiKey.modifyDate,

                        admin.name,
                        company.companyName
                ));

        return query.fetchOne();
    }

    // ApiKey 중복 조회 : param -> key, type = 1
    @Override
    public Long findByApiKeyDuplicateCount(String key, Integer type) {

        QApiKey apiKey = QApiKey.apiKey;

        JPQLQuery<Long> query = from(apiKey)
                .where(apiKey.key.eq(key).and(apiKey.type.eq(type)))
                .select(Projections.constructor(Long.class,
                        apiKey.count()
                ));

        return query.fetchOne();
    }


    // TestApiKey 만료예정 리스트 조회
    @Override
    public List<TestApiKeyExpiredListDto> findByTestApiKeyExpiredList(HashMap<String, Object> paramMap, Integer type) {

        QApiKey apiKey = QApiKey.apiKey;
        QAdmin admin = QAdmin.admin;
        QCompany company = QCompany.company;

        Date systemDate = new Date(System.currentTimeMillis());
        log.info("현재 날짜 : "+systemDate);

        // 현재 LocalDate에서 3일후인 날짜계산
        Calendar c = Calendar.getInstance();
        c.setTime(systemDate);
        c.add(Calendar.DATE, 3);

        Date threeDayAfter = new Date(c.getTimeInMillis());
        log.info("3일후 날짜 : "+threeDayAfter);

        JPQLQuery<TestApiKeyExpiredListDto> query = from(apiKey)
                .innerJoin(admin).on(admin.idx.eq(apiKey.adminIdx))
                .innerJoin(company).on(company.idx.eq(admin.companyIdx))
                .where(apiKey.type.eq(type)
                        // xml 쿼리 -> AND DATE_FORMAT(SYSDATE(), '%Y-%m-%d') = DATE_FORMAT(DATE_ADD(A.`VALIDITY_END`, INTERVAL 3 DAY), '%Y-%m-%d')
                        // 서프하고 얘기 한번 해야 할듯한 쿼리 fix
                        .and(apiKey.validityEnd.goe(systemDate).and(apiKey.validityEnd.loe(threeDayAfter))))
                .select(Projections.constructor(TestApiKeyExpiredListDto.class,
                        apiKey.idx,
                        apiKey.companyIdx,
                        apiKey.adminIdx,
                        apiKey.key,
                        apiKey.regdate,

                        apiKey.type,
                        apiKey.note,
                        apiKey.validityStart,
                        apiKey.validityEnd,

                        new CaseBuilder()
                                .when(apiKey.validityStart.loe(systemDate).and(apiKey.validityEnd.goe(systemDate))).then("Y")
                                .otherwise("N"),

                        apiKey.useAccumulate,
                        apiKey.state,
                        apiKey.useYn,

                        apiKey.reason,
                        apiKey.modifierName,
                        apiKey.modifyDate,

                        admin.name,
                        admin.email,
                        company.companyName
                ));

        return query.fetch();
    }

}

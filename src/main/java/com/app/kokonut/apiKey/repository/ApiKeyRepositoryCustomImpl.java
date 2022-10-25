package com.app.kokonut.apiKey.repository;

import com.app.kokonut.admin.entity.QAdmin;
import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.dto.ApiKeyListCountDto;
import com.app.kokonut.apiKey.dto.ApiKeyListAndDetailDto;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark :
 */
@Slf4j
@Repository
public class ApiKeyRepositoryCustomImpl extends QuerydslRepositorySupport implements ApiKeyRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    JpaResultMapper jpaResultMapper;

    public ApiKeyRepositoryCustomImpl(JdbcTemplate jdbcTemplate) {
        super(ApiKey.class);
        this.jdbcTemplate = jdbcTemplate;
    }

    // ApiKey 리스트 조회
    @Override
    public List<ApiKeyListAndDetailDto> findByApiKeyList(HashMap<String, Object> paramMap) {

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

        if(!paramMap.get("useYn").equals("") && paramMap.get("useYn") != null){
            query.where(apiKey.useYn.eq(String.valueOf(paramMap.get("useYn"))));
        }

        if(!paramMap.get("state").equals("") && paramMap.get("state") != null){
            query.where(apiKey.state.eq(Integer.valueOf(String.valueOf(paramMap.get("state")))));
        }

        if(!paramMap.get("type").equals("") && paramMap.get("type") != null){
            query.where(apiKey.type.eq(Integer.valueOf(String.valueOf(paramMap.get("type")))));
        }

        if(!paramMap.get("companyIdx").equals("") && paramMap.get("companyIdx") != null){
            query.where(apiKey.companyIdx.eq(Integer.valueOf(String.valueOf(paramMap.get("companyIdx")))));
        }

        if(!paramMap.get("beInUse").equals("") && paramMap.get("beInUse") != null){
            if(paramMap.get("beInUse").equals("Y")){
                query.where(apiKey.validityStart.gt(systemDate).or(apiKey.validityEnd.lt(systemDate)));
            }else{
                query.where(apiKey.validityEnd.lt(systemDate));
            }
        }

        if(!paramMap.get("stimeStart").equals("") && paramMap.get("stimeStart") != null &&
                !paramMap.get("stimeEnd").equals("") && paramMap.get("stimeEnd") != null){
            query.where(apiKey.regdate.between((java.util.Date) paramMap.get("stimeStart"), (java.util.Date) paramMap.get("stimeEnd")));
        }

        if(!paramMap.get("searchText").equals("") && paramMap.get("searchText") != null){
            query.where(apiKey.key.like("%"+ paramMap.get("searchText") +"%"));
        }

        if(!paramMap.get("dateType").equals("") && paramMap.get("dateType") != null && paramMap.get("dateType").equals("today")){
            query.where(apiKey.regdate.eq(systemDate));
        }

        return query.fetch();
    }

    // ApiKey 리스트 Count 조회
    @Override
    public ApiKeyListCountDto findByApiKeyListCount(HashMap<String, Object> paramMap) {

        QApiKey apiKey = QApiKey.apiKey;
        QAdmin admin = QAdmin.admin;
        QCompany company = QCompany.company;

        Date systemDate = new Date(System.currentTimeMillis());

        JPQLQuery<ApiKeyListCountDto> query = from(apiKey)
                .innerJoin(admin).on(admin.idx.eq(apiKey.adminIdx))
                .innerJoin(company).on(company.idx.eq(admin.companyIdx))
                .select(Projections.constructor(ApiKeyListCountDto.class,
                        apiKey.count()
                ));

        if(!paramMap.get("useYn").equals("") && paramMap.get("useYn") != null){
            query.where(apiKey.useYn.eq(String.valueOf(paramMap.get("useYn"))));
        }

        if(!paramMap.get("state").equals("") && paramMap.get("state") != null){
            query.where(apiKey.state.eq(Integer.valueOf(String.valueOf(paramMap.get("state")))));
        }

        if(!paramMap.get("type").equals("") && paramMap.get("type") != null){
            query.where(apiKey.type.eq(Integer.valueOf(String.valueOf(paramMap.get("type")))));
        }

        if(!paramMap.get("companyIdx").equals("") && paramMap.get("companyIdx") != null){
            query.where(apiKey.companyIdx.eq(Integer.valueOf(String.valueOf(paramMap.get("companyIdx")))));
        }

        if(!paramMap.get("beInUse").equals("") && paramMap.get("beInUse") != null){
            if(paramMap.get("beInUse").equals("Y")){
                query.where(apiKey.validityStart.gt(systemDate).or(apiKey.validityEnd.lt(systemDate)));
            }else{
                query.where(apiKey.validityEnd.lt(systemDate));
            }
        }

        if(!paramMap.get("stimeStart").equals("") && paramMap.get("stimeStart") != null &&
                !paramMap.get("stimeEnd").equals("") && paramMap.get("stimeEnd") != null){
            query.where(apiKey.regdate.between((java.util.Date) paramMap.get("stimeStart"), (java.util.Date) paramMap.get("stimeEnd")));
        }

        if(!paramMap.get("searchText").equals("") && paramMap.get("searchText") != null){
            query.where(apiKey.key.like("%"+ paramMap.get("searchText") +"%"));
        }

        if(!paramMap.get("dateType").equals("") && paramMap.get("dateType") != null && paramMap.get("dateType").equals("today")){
            query.where(apiKey.regdate.eq(systemDate));
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



}

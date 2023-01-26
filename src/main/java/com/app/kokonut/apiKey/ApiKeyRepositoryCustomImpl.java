package com.app.kokonut.apiKey;

import com.app.kokonut.admin.entity.QAdmin;
import com.app.kokonut.apiKey.dtos.ApiKeyListAndDetailDto;
import com.app.kokonut.apiKey.dtos.ApiKeyMapperDto;
import com.app.kokonut.company.QCompany;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

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




//
//    // ApiKey 리스트 조회
//    @Override
//    public List<ApiKeyListAndDetailDto> findByApiKeyList(ApiKeyMapperDto apiKeyMapperDto) {
//
//        QApiKey apiKey = QApiKey.apiKey;
//        QAdmin admin = QAdmin.admin;
//        QCompany company = QCompany.company;
//
//        LocalDateTime nowDate = LocalDateTime.now();
//
//        JPQLQuery<ApiKeyListAndDetailDto> query = from(apiKey)
//                .innerJoin(admin).on(admin.adminId.eq(apiKey.adminId))
//                .innerJoin(company).on(company.companyId.eq(admin.companyId))
//                .select(Projections.constructor(ApiKeyListAndDetailDto.class,
//                        apiKey.akId,
//                        apiKey.companyId,
//                        apiKey.adminId,
//                        apiKey.akKey,
//                        apiKey.insert_date,
//
//                        apiKey.akType,
//                        apiKey.akNote,
//
//                        apiKey.akUseAccumulate,
//                        apiKey.akState,
//                        apiKey.akUseYn,
//
//                        apiKey.akReason,
//
//                        apiKey.modify_email,
//                        apiKey.modify_date,
//
//                        admin.knName,
//                        company.cpName
//                ));
//
//        if(apiKeyMapperDto.getUseYn() != null) {
//            query.where(apiKey.akUseYn.eq(String.valueOf(apiKeyMapperDto.getUseYn())));
//        }
//
//        if(apiKeyMapperDto.getType() != null) {
//            query.where(apiKey.akType.eq(apiKeyMapperDto.getType()));
//        }
//
//        if(apiKeyMapperDto.getBeInUse() != null) {
//            if(apiKeyMapperDto.getBeInUse().equals("Y")){
//                query.where(apiKey.akValidityStart.gt(nowDate).or(apiKey.akValidityEnd.lt(nowDate)));
//            }else{
//                query.where(apiKey.akValidityEnd.lt(nowDate));
//            }
//        }
//
//        if(apiKeyMapperDto.getStimeStart() != null && apiKeyMapperDto.getStimeEnd() != null){
//            query.where(apiKey.insert_date.between(apiKeyMapperDto.getStimeStart(), apiKeyMapperDto.getStimeEnd()));
//        }
//
//        if(apiKeyMapperDto.getSearchText() != null){
//            query.where(apiKey.akKey.like("%"+ apiKeyMapperDto.getSearchText() +"%"));
//        }
//
//        return query.fetch();
//    }
//
//    // ApiKey 리스트 Count 조회
//    @Override
//    public Long findByApiKeyListCount(ApiKeyMapperDto apiKeyMapperDto) {
//
//        QApiKey apiKey = QApiKey.apiKey;
//        QAdmin admin = QAdmin.admin;
//        QCompany company = QCompany.company;
//
//        Date systemDate = new Date(System.currentTimeMillis());
//
//        JPQLQuery<Long> query = from(apiKey)
//                .innerJoin(admin).on(admin.adminId.eq(apiKey.adminId))
//                .innerJoin(company).on(company.companyId.eq(admin.companyId))
//                .select(Projections.constructor(Long.class,
//                        apiKey.count()
//                ));
//
//        if(apiKeyMapperDto.getUseYn() != null){
//            query.where(apiKey.akUseYn.eq(String.valueOf(apiKeyMapperDto.getUseYn())));
//        }
//
//        if(apiKeyMapperDto.getType() != null){
//            query.where(apiKey.akType.eq(apiKeyMapperDto.getType()));
//        }
//
//        if(apiKeyMapperDto.getBeInUse() != null){
//            if(apiKeyMapperDto.getBeInUse().equals("Y")){
//                query.where(apiKey.validityStart.gt(systemDate).or(apiKey.validityEnd.lt(systemDate)));
//            }else{
//                query.where(apiKey.validityEnd.lt(systemDate));
//            }
//        }
//
//        if(apiKeyMapperDto.getStimeStart() != null && apiKeyMapperDto.getStimeEnd() != null){
//            query.where(apiKey.regdate.between(apiKeyMapperDto.getStimeStart(), apiKeyMapperDto.getStimeEnd()));
//        }
//
//        if(apiKeyMapperDto.getSearchText() != null){
//            query.where(apiKey.key.like("%"+ apiKeyMapperDto.getSearchText() +"%"));
//        }
//
//        return query.fetchOne();
//    }
//
//    // ApiKey 상세보기
//    @Override
//    public ApiKeyListAndDetailDto findByApiKeyDetail(Long akId) {
//
//        QApiKey apiKey = QApiKey.apiKey;
//        QAdmin admin = QAdmin.admin;
//        QCompany company = QCompany.company;
//
//        Date systemDate = new Date(System.currentTimeMillis());
//
//        JPQLQuery<ApiKeyListAndDetailDto> query = from(apiKey)
//                .innerJoin(admin).on(admin.adminId.eq(apiKey.adminId))
//                .innerJoin(company).on(company.companyId.eq(admin.companyId))
//                .where(apiKey.akId.eq(akId))
//                .select(Projections.constructor(ApiKeyListAndDetailDto.class,
//                        apiKey.akId,
//                        apiKey.companyId,
//                        apiKey.adminId,
//                        apiKey.akKey,
//                        apiKey.insert_date,
//
//                        apiKey.akType,
//                        apiKey.akNote,
//
//                        apiKey.akUseAccumulate,
//                        apiKey.akState,
//                        apiKey.akUseYn,
//
//                        apiKey.reason,
//                        apiKey.modifierIdx,
//                        apiKey.modifierName,
//                        apiKey.modifyDate,
//
//                        admin.name,
//                        company.companyName
//                ));
//
//        return query.fetchOne();
//    }
//
//    // ApiKey 단일 조회
//    @Override
//    public ApiKeyKeyDto findByKey(String key) {
//        QApiKey apiKey = QApiKey.apiKey;
//
//        JPQLQuery<ApiKeyKeyDto> query = from(apiKey)
//                .where(apiKey.key.eq(key))
//                .select(Projections.constructor(ApiKeyKeyDto.class,
//                        apiKey.idx,
//                        apiKey.companyId,
//                        apiKey.adminId,
//                        apiKey.registerName,
//                        apiKey.key,
//                        apiKey.regdate,
//                        apiKey.akType,
//                        apiKey.note,
//
//                        apiKey.validityStart,
//                        apiKey.validityEnd,
//
//                        apiKey.useAccumulate,
//                        apiKey.state,
//                        apiKey.akUseYn,
//
//                        apiKey.reason,
//                        apiKey.modifierIdx,
//                        apiKey.modifierName,
//                        apiKey.modifyDate
//                ));
//
//        return query.fetchOne();
//    }
//
//    // Test Api Key 조회 : param -> companyId
//    @Override
//    public ApiKeyListAndDetailDto findByTestApiKeyBycompanyId(Long companyId, Integer type) {
//
//        QApiKey apiKey = QApiKey.apiKey;
//        QAdmin admin = QAdmin.admin;
//        QCompany company = QCompany.company;
//
//        Date systemDate = new Date(System.currentTimeMillis());
//
//        JPQLQuery<ApiKeyListAndDetailDto> query = from(apiKey)
//                .innerJoin(admin).on(admin.adminId.eq(apiKey.adminId))
//                .innerJoin(company).on(company.companyId.eq(admin.companyId))
//                .where(apiKey.companyId.eq(companyId).and(apiKey.akType.eq(type)))
//                .select(Projections.constructor(ApiKeyListAndDetailDto.class,
//                        apiKey.idx,
//                        apiKey.companyId,
//                        apiKey.adminId,
//                        apiKey.key,
//                        apiKey.regdate,
//
//                        apiKey.akType,
//                        apiKey.note,
//                        apiKey.validityStart,
//                        apiKey.validityEnd,
//
//                        new CaseBuilder()
//                                .when(apiKey.validityStart.loe(systemDate).and(apiKey.validityEnd.goe(systemDate))).then("Y")
//                                .otherwise("N"),
//
//                        apiKey.useAccumulate,
//                        apiKey.state,
//                        apiKey.akUseYn,
//
//                        apiKey.reason,
//                        apiKey.modifierIdx,
//                        apiKey.modifierName,
//                        apiKey.modifyDate,
//
//                        admin.name,
//                        company.companyName
//                ));
//
//        return query.fetchOne();
//    }
//
//    // TestApiKey 중복 조회 : param -> key, type = 2
//    @Override
//    public Long findByTestApiKeyDuplicateCount(String key, Integer type) {
//
//        QApiKey apiKey = QApiKey.apiKey;
//
//        JPQLQuery<Long> query = from(apiKey)
//                .where(apiKey.key.eq(key).and(apiKey.akType.eq(type)))
//                .select(Projections.constructor(Long.class,
//                        apiKey.count()
//                ));
//
//        return query.fetchOne();
//    }
//
//    // ApiKey 단일 조회 : param -> companyId, type = 1, useYn = "Y"
//    @Override
//    public ApiKeyListAndDetailDto findByApiKeyBycompanyId(Long companyId, Integer type, String useYn) {
//
//        QApiKey apiKey = QApiKey.apiKey;
//        QAdmin admin = QAdmin.admin;
//        QCompany company = QCompany.company;
//
//        Date systemDate = new Date(System.currentTimeMillis());
//
//        JPQLQuery<ApiKeyListAndDetailDto> query = from(apiKey)
//                .innerJoin(admin).on(admin.adminId.eq(apiKey.adminId))
//                .innerJoin(company).on(company.companyId.eq(admin.companyId))
//                .where(apiKey.companyId.eq(companyId).and(apiKey.akType.eq(type).and(apiKey.akUseYn.eq(useYn)))).limit(1)
//                .select(Projections.constructor(ApiKeyListAndDetailDto.class,
//                        apiKey.idx,
//                        apiKey.companyId,
//                        apiKey.adminId,
//                        apiKey.key,
//                        apiKey.regdate,
//
//                        apiKey.akType,
//                        apiKey.note,
//                        apiKey.validityStart,
//                        apiKey.validityEnd,
//
//                        new CaseBuilder()
//                                .when(apiKey.validityStart.loe(systemDate).and(apiKey.validityEnd.goe(systemDate))).then("Y")
//                                .otherwise("N"),
//
//                        apiKey.useAccumulate,
//                        apiKey.state,
//                        apiKey.akUseYn,
//
//                        apiKey.reason,
//                        apiKey.modifierIdx,
//                        apiKey.modifierName,
//                        apiKey.modifyDate,
//
//                        admin.name,
//                        company.companyName
//                ));
//
//        return query.fetchOne();
//    }
//
//    // ApiKey 중복 조회 : param -> key, type = 1
//    @Override
//    public Long findByApiKeyDuplicateCount(String key, Integer type) {
//
//        QApiKey apiKey = QApiKey.apiKey;
//
//        JPQLQuery<Long> query = from(apiKey)
//                .where(apiKey.key.eq(key).and(apiKey.akType.eq(type)))
//                .select(Projections.constructor(Long.class,
//                        apiKey.count()
//                ));
//
//        return query.fetchOne();
//    }
//
//
//    // TestApiKey 만료예정 리스트 조회
//    @Override
//    public List<TestApiKeyExpiredListDto> findByTestApiKeyExpiredList(HashMap<String, Object> paramMap, Integer type) {
//
//        QApiKey apiKey = QApiKey.apiKey;
//        QAdmin admin = QAdmin.admin;
//        QCompany company = QCompany.company;
//
//        Date systemDate = new Date(System.currentTimeMillis());
//        log.info("현재 날짜 : "+systemDate);
//
//        // 현재 LocalDate에서 3일후인 날짜계산
//        Calendar c = Calendar.getInstance();
//        c.setTime(systemDate);
//        c.add(Calendar.DATE, 3);
//
//        Date threeDayAfter = new Date(c.getTimeInMillis());
//        log.info("3일후 날짜 : "+threeDayAfter);
//
//        JPQLQuery<TestApiKeyExpiredListDto> query = from(apiKey)
//                .innerJoin(admin).on(admin.adminId.eq(apiKey.adminId))
//                .innerJoin(company).on(company.companyId.eq(admin.companyId))
//                .where(apiKey.akType.eq(type)
//                        // xml 쿼리 -> AND DATE_FORMAT(SYSDATE(), '%Y-%m-%d') = DATE_FORMAT(DATE_ADD(A.`VALIDITY_END`, INTERVAL 3 DAY), '%Y-%m-%d')
//                        // 서프하고 얘기 한번 해야 할듯한 쿼리 fix
//                        .and(apiKey.validityEnd.goe(systemDate).and(apiKey.validityEnd.loe(threeDayAfter))))
//                .select(Projections.constructor(TestApiKeyExpiredListDto.class,
//                        apiKey.idx,
//                        apiKey.companyId,
//                        apiKey.adminId,
//                        apiKey.key,
//                        apiKey.regdate,
//
//                        apiKey.akType,
//                        apiKey.note,
//                        apiKey.validityStart,
//                        apiKey.validityEnd,
//
//                        new CaseBuilder()
//                                .when(apiKey.validityStart.loe(systemDate).and(apiKey.validityEnd.goe(systemDate))).then("Y")
//                                .otherwise("N"),
//
//                        apiKey.useAccumulate,
//                        apiKey.state,
//                        apiKey.akUseYn,
//
//                        apiKey.reason,
//                        apiKey.modifierName,
//                        apiKey.modifyDate,
//
//                        admin.name,
//                        admin.email,
//                        company.companyName
//                ));
//
//        return query.fetch();
//    }
//
//    // ApiKey가 존재하는지 그리고 유효한지 검증하는 메서드
//    @Override
//    public Long findByCheck(String key, String email) {
//
//        QApiKey apiKey = QApiKey.apiKey;
//        QAdmin admin = QAdmin.admin;
//
//        JPQLQuery<Long> query = from(apiKey)
//                .innerJoin(admin).on(apiKey.adminId.eq(admin.adminId))
//                .where(apiKey.key.eq(key).and(admin.email.eq(email)))
//                .select(Projections.constructor(Long.class,
//                        apiKey.count()
//                ));
//
//        return query.fetchOne();
//    }

}

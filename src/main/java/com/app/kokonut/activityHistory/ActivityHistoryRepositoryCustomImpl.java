package com.app.kokonut.activityHistory;

import com.app.kokonut.activityHistory.dto.*;
import com.app.kokonut.woody.been.Column;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Woody
 * Date : 2022-11-03
 * Time :
 * Remark : ActivityHistoryRepositoryCustom 쿼리문 선언부
 */
@Repository
public class ActivityHistoryRepositoryCustomImpl extends QuerydslRepositorySupport implements ActivityHistoryRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public ActivityHistoryRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(ActivityHistory.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    // ActivityHistory 리스트 조회
    @Override
    public List<ActivityHistoryListDto> findByActivityHistoryList(ActivityHistorySearchDto activityHistorySearchDto) {

        // SQL Query
//        select
//        a.IDX, a.COMPANY_IDX, a.ADMIN_IDX, a.ACTIVITY_IDX, a.ACTIVITY_DETAIL,
//                CASE
//        WHEN CHAR_LENGTH(b.NAME) > 2
//        THEN
//        CONCAT(
//                SUBSTRING(b.NAME, 1, 1),
//                LPAD('*', CHAR_LENGTH(b.NAME) - 2, '*'),
//                SUBSTRING(NAME, CHAR_LENGTH(b.NAME), CHAR_LENGTH(b.NAME))
//        )
//        ELSE
//        CONCAT(
//                SUBSTRING(b.NAME, 1, 1),
//                LPAD('*', CHAR_LENGTH(b.NAME) - 1, '*')
//        )
//        END as maskingName,
//                b.NAME, b.EMAIL, c.LEVEL, d.ACTIVITY AS isActivity, d.TYPE
//
//        FROM activity_history a
//        INNER JOIN admin b ON b.IDX = a.ADMIN_IDX
//        LEFT JOIN admin_level c ON c.IDX = b.ADMIN_LEVEL_IDX
//        INNER JOIN activity d ON  d.IDX = a.ACTIVITY_IDX
//        WHERE 1 = 1
//        ORDER BY a.REGDATE DESC

        EntityManager em = getEntityManager();
        StringBuilder sb = new StringBuilder();

        // 네이티브 쿼리문
        sb.append("SELECT \n");
        sb.append("a.IDX, a.COMPANY_IDX, a.ADMIN_IDX, a.ACTIVITY_IDX, a.ACTIVITY_DETAIL, \n");
        sb.append("a.REASON, a.IP_ADDR, a.REGDATE, a.STATE, \n");
        sb.append("CASE \n");
        sb.append("WHEN CHAR_LENGTH(b.NAME) > 2 \n");
        sb.append("THEN \n");
        sb.append("CONCAT(SUBSTRING(b.NAME, 1, 1), \n");
        sb.append("LPAD('*', CHAR_LENGTH(b.NAME) - 2, '*'), \n");
        sb.append("SUBSTRING(NAME, CHAR_LENGTH(b.NAME), CHAR_LENGTH(b.NAME))) \n");
        sb.append("ELSE \n");
        sb.append("CONCAT(SUBSTRING(b.NAME, 1, 1), \n");
        sb.append("LPAD('*', CHAR_LENGTH(b.NAME) - 1, '*')) \n");
        sb.append("END as maskingName, \n");
        sb.append("b.NAME, b.EMAIL, c.LEVEL, d.ACTIVITY AS isActivity, d.TYPE, \n");
        sb.append("CASE \n");
        sb.append("WHEN d.TYPE = 1 THEN '고객정보처리' \n");
        sb.append("WHEN d.TYPE = 2 THEN '관리자활동' \n");
        sb.append("WHEN d.TYPE = 3 THEN '회원DB관리이력' \n");
        sb.append("END as typeString, \n");
        sb.append("CASE \n");
        sb.append("WHEN a.STATE = 1 THEN '정상' \n");
        sb.append("WHEN a.STATE = 0 THEN '비정상' \n");
        sb.append("END as stateString \n");
        sb.append("FROM activity_history a \n");
        sb.append("INNER JOIN admin b ON b.IDX = a.ADMIN_IDX \n");
        sb.append("LEFT JOIN admin_level c ON c.IDX = b.ADMIN_LEVEL_IDX \n");
        sb.append("INNER JOIN activity d ON  d.IDX = a.ACTIVITY_IDX \n");

        sb.append("WHERE 1=1 \n");

        if(activityHistorySearchDto.getType() != null){
            if(activityHistorySearchDto.getType() == 4){
                sb.append("AND d.TYPE IN ('2','3') \n");
            }else{
                sb.append("AND d.TYPE = :type \n");
            }
        }

        if(activityHistorySearchDto.getActivityIdx() != null){
            sb.append("AND a.ACTIVITY_IDX = :activityIdx \n");
        }

        if(activityHistorySearchDto.getCompanyIdx() != null){
            sb.append("AND a.COMPANY_IDX = :companyIdx \n");
        }

        if(activityHistorySearchDto.getStimeStart() != null && activityHistorySearchDto.getStimeEnd() != null){
            sb.append("AND a.REGDATE BETWEEN :stimeStart AND :stimeEnd \n");
        }

        if(activityHistorySearchDto.getSearchText() != null){
            sb.append("AND b.NAME LIKE CONCAT('%',:searchText,'%') OR b.EMAIL LIKE CONCAT('%',:searchText,'%') \n");
        }

        sb.append("ORDER BY a.REGDATE DESC; \n");

        // 쿼리조건 선언부
        Query query = em.createNativeQuery(sb.toString());

        if(activityHistorySearchDto.getType() != null){
            if(activityHistorySearchDto.getType() != 4){
                query.setParameter("type", activityHistorySearchDto.getType());
            }
        }

        if(activityHistorySearchDto.getActivityIdx() != null){
            query.setParameter("activityIdx", activityHistorySearchDto.getActivityIdx());
        }

        if(activityHistorySearchDto.getCompanyIdx() != null){
            query.setParameter("companyIdx", activityHistorySearchDto.getCompanyIdx());
        }

        if(activityHistorySearchDto.getStimeStart() != null && activityHistorySearchDto.getStimeEnd() != null){
            query.setParameter("stimeStart", activityHistorySearchDto.getStimeStart());
            query.setParameter("stimeEnd", activityHistorySearchDto.getStimeEnd());
        }

        if(activityHistorySearchDto.getSearchText() != null){
            query.setParameter("searchText", activityHistorySearchDto.getSearchText());
        }

        return jpaResultMapper.list(query, ActivityHistoryListDto.class);
    }

    // ActivityHistory 단일 조회
    // param : Integer idx
    @Override
    public ActivityHistoryDto findByActivityHistoryByIdx(Integer idx) {

        EntityManager em = getEntityManager();
        StringBuilder sb = new StringBuilder();

        // 네이티브 쿼리문
        sb.append("SELECT \n");
        sb.append("a.IDX, a.COMPANY_IDX, a.ADMIN_IDX, a.ACTIVITY_IDX, a.ACTIVITY_DETAIL, \n");
        sb.append("a.REASON, a.IP_ADDR, a.REGDATE, a.STATE, \n");
        sb.append("CASE \n");
        sb.append("WHEN CHAR_LENGTH(b.NAME) > 2 \n");
        sb.append("THEN \n");
        sb.append("CONCAT(SUBSTRING(b.NAME, 1, 1), \n");
        sb.append("LPAD('*', CHAR_LENGTH(b.NAME) - 2, '*'), \n");
        sb.append("SUBSTRING(NAME, CHAR_LENGTH(b.NAME), CHAR_LENGTH(b.NAME))) \n");
        sb.append("ELSE \n");
        sb.append("CONCAT(SUBSTRING(b.NAME, 1, 1), \n");
        sb.append("LPAD('*', CHAR_LENGTH(b.NAME) - 1, '*')) \n");
        sb.append("END as maskingName, \n");
        sb.append("b.NAME, b.EMAIL, c.LEVEL, d.ACTIVITY AS isActivity, d.TYPE \n");
        sb.append("FROM activity_history a \n");
        sb.append("INNER JOIN admin b ON b.IDX = a.ADMIN_IDX \n");
        sb.append("LEFT JOIN admin_level c ON c.IDX = b.ADMIN_LEVEL_IDX \n");
        sb.append("INNER JOIN activity d ON  d.IDX = a.ACTIVITY_IDX \n");

        if(idx != 0){
            sb.append("WHERE a.IDX = :idx \n");
        }

        // 쿼리조건 선언부
        Query query = em.createNativeQuery(sb.toString());

        if(idx != 0){
            query.setParameter("idx", idx);
        }

        return jpaResultMapper.uniqueResult(query, ActivityHistoryDto.class);
    }

    // ActivityHistory 단일 조회
    // param : Integer companyIdx, String reason, Integer activityIdx
    @Override
    public ActivityHistoryDto findByActivityHistoryByCompanyIdxAndReasonaAndAtivityIdx(Integer companyIdx, String reason, Integer activityIdx) {

        EntityManager em = getEntityManager();
        StringBuilder sb = new StringBuilder();

        // 네이티브 쿼리문
        sb.append("SELECT \n");
        sb.append("a.IDX, a.COMPANY_IDX, a.ADMIN_IDX, a.ACTIVITY_IDX, a.ACTIVITY_DETAIL, \n");
        sb.append("a.REASON, a.IP_ADDR, a.REGDATE, a.STATE, \n");
        sb.append("CASE \n");
        sb.append("WHEN CHAR_LENGTH(b.NAME) > 2 \n");
        sb.append("THEN \n");
        sb.append("CONCAT( \n");
        sb.append("SUBSTRING(b.NAME, 1, 1), \n");
        sb.append("LPAD('*', CHAR_LENGTH(b.NAME) - 2, '*'), \n");
        sb.append("SUBSTRING(NAME, CHAR_LENGTH(b.NAME), CHAR_LENGTH(b.NAME)) \n");
        sb.append(") \n");
        sb.append("ELSE \n");
        sb.append("CONCAT( \n");
        sb.append("CASE \n");
        sb.append("SUBSTRING(b.NAME, 1, 1), \n");
        sb.append("LPAD('*', CHAR_LENGTH(b.NAME) - 1, '*') \n");
        sb.append(") \n");
        sb.append("END as maskingName, \n");
        sb.append("b.NAME, b.EMAIL, c.LEVEL, d.ACTIVITY, d.TYPE \n");
        sb.append("FROM activity_history a \n");
        sb.append("INNER JOIN admin b ON b.IDX = a.ADMIN_IDX \n");
        sb.append("LEFT JOIN admin_level c ON c.IDX = b.ADMIN_LEVEL_IDX \n");
        sb.append("INNER JOIN activity d ON  d.IDX = a.ACTIVITY_IDX \n");
        sb.append("WHERE 1 = 1 \n");

        if(companyIdx != 0){
            sb.append("AND a.COMPANY_IDX = :companyIdx \n");
        }

        if(!reason.equals("")){
            sb.append("AND a.REASON = :reason \n");
        }

        if(activityIdx != 0){
            sb.append("AND a.ACTIVITY_IDX = :activityIdx \n");
        }

        sb.append("ORDER BY A.REGDATE DESC LIMIT 1 \n");

        // 쿼리조건 선언부
        Query query = em.createNativeQuery(sb.toString());

        if(companyIdx != 0){
            query.setParameter("companyIdx", companyIdx);
        }

        if(!reason.equals("")){
            query.setParameter("reason", reason);
        }

        if(activityIdx != 0){
            query.setParameter("activityIdx", activityIdx);
        }

        return jpaResultMapper.uniqueResult(query, ActivityHistoryDto.class);
    }

    // ActivityHistory 리스트 조회
    // param : Integer companyIdx, Integer type
    @Override
    public List<ActivityHistoryInfoListDto> findByActivityHistoryByCompanyIdxAndTypeList(Integer companyIdx, Integer type) {

        QActivityHistory activityHistory = QActivityHistory.activityHistory;

        JPQLQuery<ActivityHistoryInfoListDto> query = from(activityHistory)
                .select(Projections.constructor(ActivityHistoryInfoListDto.class,
                    activityHistory.idx,
                    activityHistory.companyIdx,
                    activityHistory.adminIdx,
                    activityHistory.activityIdx,
                    activityHistory.activityDetail,
                    activityHistory.reason,
                    activityHistory.ipAddr,
                    activityHistory.regdate,
                    activityHistory.state
                ));

        if(companyIdx != null) {
            query.where(activityHistory.companyIdx.eq(companyIdx));
        }

        if(type != null) {
            query.where(activityHistory.type.eq(type));
        }

        return query.fetch();
    }

    // ActivityHistory Column 리스트 조회
    @Override
    public List<Column> findByActivityHistoryColumnList() {

        EntityManager em = getEntityManager();
        StringBuilder sb = new StringBuilder();

        // 네이티브 쿼리문
        sb.append("show full columns from activity_history \n");
//        sb.append("SHOW COLUMNS FROM activity_history \n"); // h2

        // 쿼리조건 선언부
        Query query = em.createNativeQuery(sb.toString());

        return jpaResultMapper.list(query, Column.class);
    }

    // ActivityHistory 활동내역 통계 조회
    // param : Integer companyIdx, int day
    @Override
    public ActivityHistoryStatisticsDto findByActivityHistoryStatistics(Integer companyIdx, int day) {

        EntityManager em = getEntityManager();
        StringBuilder sb = new StringBuilder();

        // 네이티브 쿼리문
        sb.append("SELECT \n");
        sb.append("DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :day DAY), '%Y-%m-%d') AS date, \n");
        sb.append("(SELECT COUNT(*) FROM admin a WHERE DATE(a.REGDATE) = DATE_SUB(NOW(), INTERVAL :day DAY) AND a.COMPANY_IDX = :companyIdx) AS newMember, \n");
        sb.append("(SELECT COUNT(*) FROM activity_history a INNER JOIN activity b ON a.ACTIVITY_IDX = b.IDX \n");
        sb.append("WHERE a.COMPANY_IDX = :companyIdx AND b.TYPE = 1 \n");
        sb.append("AND DATE_FORMAT(a.REGDATE, '%Y-%m-%d') = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :day DAY), '%Y-%m-%d')) AS personalHistory, \n");
        sb.append("(SELECT COUNT(*) FROM activity_history a INNER JOIN activity b ON a.ACTIVITY_IDX = b.IDX \n");
        sb.append("WHERE a.COMPANY_IDX = :companyIdx AND b.TYPE = 2 \n");
        sb.append("AND DATE_FORMAT(a.REGDATE, '%Y-%m-%d') = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :day DAY), '%Y-%m-%d')) AS adminHistory, \n");
        sb.append("(SELECT COUNT(*) FROM activity_history a INNER JOIN `activity` b ON a.ACTIVITY_IDX = b.IDX \n");
        sb.append("WHERE a.COMPANY_IDX = :companyIdx AND b.TYPE = 2 AND b.ACTIVITY = 1 \n");
        sb.append("AND DATE_FORMAT(a.REGDATE, '%Y-%m-%d') = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :day DAY), '%Y-%m-%d')) AS loginCount \n");
        sb.append("; \n");

        // 쿼리조건 선언부
        Query query = em.createNativeQuery(sb.toString());

//        query.setParameter("companyIdx", companyIdx);
        query.setParameter("day", day);

        return jpaResultMapper.uniqueResult(query, ActivityHistoryStatisticsDto.class);
    }

    @Override
    public void deleteExpiredActivityHistory(int activityIdx, int month) {

        EntityManager em = getEntityManager();
        StringBuilder sb = new StringBuilder();

        // 네이티브 쿼리문
        sb.append("DELETE \n");
        sb.append("FROM \n");
        sb.append("activity_history \n");
        sb.append("WHERE ACTIVITY_IDX = :activityIdx \n");
        sb.append("AND REGDATE < DATE_SUB(NOW(), INTERVAL :month MONTH) \n");

        // 쿼리조건 선언부
        Query query = em.createNativeQuery(sb.toString());

        query.setParameter("activityIdx", activityIdx);
        query.setParameter("month", month);

        query.executeUpdate();
    }


}
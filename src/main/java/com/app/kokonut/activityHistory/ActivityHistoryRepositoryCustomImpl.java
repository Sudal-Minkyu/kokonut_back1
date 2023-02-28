package com.app.kokonut.activityHistory;

import com.app.kokonut.activityHistory.dto.*;
import com.app.kokonut.admin.QAdmin;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    // ActivityHistory 리스트 조회 -> querydsl방식으로 변경 : 관리자활동 이력(ah_type = 2)
    @Override
    public Page<ActivityHistoryListDto> findByActivityHistoryList(ActivityHistorySearchDto activityHistorySearchDto, Pageable pageable) {
        QActivityHistory activityHistory = QActivityHistory.activityHistory;
        QAdmin admin = QAdmin.admin;

        JPQLQuery<ActivityHistoryListDto> query = from(activityHistory)
                .innerJoin(admin).on(admin.adminId.eq(activityHistory.adminId))
                .select(Projections.constructor(ActivityHistoryListDto.class,
                        admin.knName,
                        admin.knEmail,
                        admin.knRoleCode,
                        activityHistory.activityCode,
                        activityHistory.ahActivityDetail,
                        activityHistory.insert_date,
                        activityHistory.ahIpAddr,
                        activityHistory.ahState
                ));

        // 조회한 기업의 한해서만 조회되야함
        query.where(admin.companyId.eq(activityHistorySearchDto.getCompanyId())).orderBy(activityHistory.ahId.desc());;

        if(!activityHistorySearchDto.getSearchText().equals("")) {
            query.where(admin.knEmail.like("%"+ activityHistorySearchDto.getSearchText() +"%").or(admin.knName.like("%"+ activityHistorySearchDto.getSearchText() +"%")));
        }

        if(activityHistorySearchDto.getActivityCodeList() != null) {
            query.where(activityHistory.activityCode.in(activityHistorySearchDto.getActivityCodeList()));
        }

        if(activityHistorySearchDto.getStimeStart() != null && activityHistorySearchDto.getStimeEnd() != null) {
            query.where(activityHistory.insert_date.goe(activityHistorySearchDto.getStimeStart()).and(activityHistory.insert_date.loe(activityHistorySearchDto.getStimeEnd())));
        }

//        query.where(activityHistory.ahType.eq(2));

        final List<ActivityHistoryListDto> activityHistoryListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(activityHistoryListDtos, pageable, query.fetchCount());
    }

    // ActivityHistory 단일 조회
    // param : Integer idx
    @Override
    public ActivityHistoryDto findByActivityHistoryByIdx(Long ahId) {

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

        if(ahId != 0){
            sb.append("WHERE a.ah_id = :ahId \n");
        }

        // 쿼리조건 선언부
        Query query = em.createNativeQuery(sb.toString());

        if(ahId != 0){
            query.setParameter("ahId", ahId);
        }

        return jpaResultMapper.uniqueResult(query, ActivityHistoryDto.class);
    }

    // ActivityHistory 단일 조회
    // param : Long companyId, String reason, Integer activityIdx
    @Override
    public ActivityHistoryDto findByActivityHistoryBycompanyIdAndReasonaAndAtivityIdx(Long companyId, String ahReason) {

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

        if(companyId != 0){
            sb.append("AND a.company_id = :companyId \n");
        }

        if(!ahReason.equals("")){
            sb.append("AND a.ah_reason = :ahReason \n");
        }

        sb.append("ORDER BY A.REGDATE DESC LIMIT 1 \n");

        // 쿼리조건 선언부
        Query query = em.createNativeQuery(sb.toString());

        if(companyId != 0){
            query.setParameter("companyId", companyId);
        }

        if(!ahReason.equals("")){
            query.setParameter("ahReason", ahReason);
        }

        return jpaResultMapper.uniqueResult(query, ActivityHistoryDto.class);
    }

    // ActivityHistory 리스트 조회
    // param : Long companyId, Integer type
    @Override
    public List<ActivityHistoryInfoListDto> findByActivityHistoryBycompanyIdAndTypeList(Long companyId, Integer ahType) {

        QActivityHistory activityHistory = QActivityHistory.activityHistory;
        QAdmin admin = QAdmin.admin;

        JPQLQuery<ActivityHistoryInfoListDto> query = from(activityHistory)
                .innerJoin(admin).on(admin.adminId.eq(activityHistory.adminId))
                .select(Projections.constructor(ActivityHistoryInfoListDto.class,
                    activityHistory.ahId,
                    activityHistory.adminId,
                    activityHistory.activityCode,
                    activityHistory.ahActivityDetail,
                    activityHistory.ahReason,
                    activityHistory.ahIpAddr,
                    activityHistory.ahState,
                    activityHistory.insert_email
                ));

        if(companyId != null) {
            query.where(admin.companyId.eq(companyId));
        }

        if(ahType != null) {
            query.where(activityHistory.ahType.eq(ahType));
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
    // param : Long companyId, int day
    @Override
    public ActivityHistoryStatisticsDto findByActivityHistoryStatistics(Long companyId, int day) {

        EntityManager em = getEntityManager();
        StringBuilder sb = new StringBuilder();

        // 네이티브 쿼리문
        sb.append("SELECT \n");
        sb.append("DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :day DAY), '%Y-%m-%d') AS date, \n");
        sb.append("(SELECT COUNT(*) FROM admin a WHERE DATE(a.REGDATE) = DATE_SUB(NOW(), INTERVAL :day DAY) AND a.COMPANY_IDX = :companyId) AS newMember, \n");
        sb.append("(SELECT COUNT(*) FROM activity_history a INNER JOIN activity b ON a.ACTIVITY_IDX = b.IDX \n");
        sb.append("WHERE a.COMPANY_IDX = :companyId AND b.TYPE = 1 \n");
        sb.append("AND DATE_FORMAT(a.REGDATE, '%Y-%m-%d') = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :day DAY), '%Y-%m-%d')) AS personalHistory, \n");
        sb.append("(SELECT COUNT(*) FROM activity_history a INNER JOIN activity b ON a.ACTIVITY_IDX = b.IDX \n");
        sb.append("WHERE a.COMPANY_IDX = :companyId AND b.TYPE = 2 \n");
        sb.append("AND DATE_FORMAT(a.REGDATE, '%Y-%m-%d') = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :day DAY), '%Y-%m-%d')) AS adminHistory, \n");
        sb.append("(SELECT COUNT(*) FROM activity_history a INNER JOIN `activity` b ON a.ACTIVITY_IDX = b.IDX \n");
        sb.append("WHERE a.COMPANY_IDX = :companyId AND b.TYPE = 2 AND b.ACTIVITY = 1 \n");
        sb.append("AND DATE_FORMAT(a.REGDATE, '%Y-%m-%d') = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :day DAY), '%Y-%m-%d')) AS loginCount \n");
        sb.append("; \n");

        // 쿼리조건 선언부
        Query query = em.createNativeQuery(sb.toString());

//        query.setParameter("companyId", companyId);
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

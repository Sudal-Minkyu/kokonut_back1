package com.app.kokonut.email.email;

import com.app.kokonut.email.email.dto.EmailDetailDto;
import com.app.kokonut.email.email.dto.EmailListDto;
import com.app.kokonut.email.email.entity.Email;
import com.app.kokonut.email.email.entity.QEmail;
import com.app.kokonut.email.emailGroup.entity.QEmailGroup;
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
import java.util.List;
import java.util.Objects;

/**
 * @author joy
 * Date : 2022-12-19
 * Time :
 * Remark : EmailRepositoryCustom 쿼리문 선언부
 */
@Repository
public class EmailRepositoryCustomImpl extends QuerydslRepositorySupport implements EmailRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public EmailRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(Email.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    // 이메일 발송 목록 호출
    @Override
    public Page<EmailListDto> findByEmailPage(Pageable pageable) {

        QEmail email  = QEmail.email;
        QEmailGroup emailGroup  = QEmailGroup.emailGroup;

        JPQLQuery<EmailListDto> query = from(email)
                .leftJoin(emailGroup).on(emailGroup.idx.eq(email.emailGroupIdx))
                .select(Projections.constructor(EmailListDto.class,
                        email.idx,
                        email.receiverType,
                        email.receiverAdminIdxList,
                        email.emailGroupIdx,
                        email.title,
                        email.contents,
                        email.regdate,
                        emailGroup.name,
                        emailGroup.desc
                        ));
        query.orderBy(email.regdate.desc());

        final List<EmailListDto> emailListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(emailListDtos, pageable, query.fetchCount());
    }

    // 이메일 상세 조회
    // param : Integer idx
    @Override
    public EmailDetailDto findEmailByIdx(Integer idx) {
        /*
         * SELECT `SENDER_ADMIN_IDX'
         * 		, `RECEIVER_TYPE`,
         * 		, `RECEIVER_ADMIN_IDX_LIST`,
         * 		, `EMAIL_GROUP_IDX`,
         * 		, `TITLE`,
         * 		, `CONTENTS`
         *   FROM `email`
         * 	WHERE `IDX`=#{idx}
         */

        QEmail email = QEmail.email;

        JPQLQuery<EmailDetailDto> query = from(email)
                .where(email.idx.eq(idx))
                .select(Projections.constructor(EmailDetailDto.class,
                        email.senderAdminIdx,
                        email.receiverType,
                        email.receiverAdminIdxList,
                        email.emailGroupIdx,
                        email.title,
                        email.contents
                ));

        return query.fetchOne();
    }

    @Override
    public Integer saveEmail(EmailDetailDto emailDetailDto) {
        /*
         * INSERT INTO `email` (
         *             `SENDER_ADMIN_IDX'
         * 		     , `RECEIVER_TYPE`,
         * 		     , `RECEIVER_ADMIN_IDX_LIST`,
         * 		     , `EMAIL_GROUP_IDX`,
         * 		     , `TITLE`,
         * 		     , `CONTENTS`
         *      ) VALUES (
         *         ...
         *      )
         */

        EntityManager em = getEntityManager();
        StringBuilder sb = new StringBuilder();

        // 네이티브 쿼리문
        sb.append("INSERT INTO email (\n");
        sb.append("'SENDER_ADMIN_IDX', \n");
        sb.append("'RECEIVER_TYPE', \n");
        // 조건에 따른 분기 처리
        if(emailDetailDto.getReceiverType().equals("I")
                && emailDetailDto.getReceiverAdminIdxList() != null
                && emailDetailDto.getReceiverAdminIdxList().equals("")){
            sb.append("'RECEIVER_ADMIN_IDX_LIST', \n");
        }
        // 조건에 따른 분기 처리
        if(emailDetailDto.getReceiverType().equals("G")
                && emailDetailDto.getEmailGroupIdx() != null
                && !emailDetailDto.getEmailGroupIdx().equals(0)){
            sb.append("'EMAIL_GROUP_IDX', \n");
        }
        sb.append("'TITLE',\n");
        sb.append("'CONTENTS'\n");
        sb.append(") VALUES (\n");
        sb.append(":senderAdminIdx, \n");
        sb.append(":receiverType, \n");
        // 조건에 따른 분기 처리
        if(emailDetailDto.getReceiverType().equals("I")
                && emailDetailDto.getReceiverAdminIdxList() != null
                && emailDetailDto.getReceiverAdminIdxList().equals("")){
            sb.append(":receiverAdminIdxList, \n");
        }
        // 조건에 따른 분기 처리
        if(emailDetailDto.getReceiverType().equals("G")
                && emailDetailDto.getEmailGroupIdx() != null
                && !emailDetailDto.getEmailGroupIdx().equals(0)){
            sb.append(":emailGroupIdx, \n");
        }
        sb.append(":title, \n");
        sb.append(":contents, \n");
        sb.append(")");


        // 쿼리 생성
        Query query = em.createNativeQuery(sb.toString());

        // 쿼리 파라미터 세팅
        query.setParameter("senderAdminIdx", emailDetailDto.getSenderAdminIdx());
        query.setParameter("receiverType", emailDetailDto.getReceiverType());

        if(emailDetailDto.getReceiverType().equals("I")
                && emailDetailDto.getReceiverAdminIdxList() != null
                && emailDetailDto.getReceiverAdminIdxList().equals("")){
            query.setParameter("receiverAdminIdxList", emailDetailDto.getReceiverAdminIdxList());
        }
        if(emailDetailDto.getReceiverType().equals("G")
                && emailDetailDto.getEmailGroupIdx() != null
                && !emailDetailDto.getEmailGroupIdx().equals(0)){
            query.setParameter("emailGroupIdx", emailDetailDto.getEmailGroupIdx());
        }

        query.setParameter("title", emailDetailDto.getTitle());
        query.setParameter("contents", emailDetailDto.getContents());

        return query.executeUpdate();
    }
}

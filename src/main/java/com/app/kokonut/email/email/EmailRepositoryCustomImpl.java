package com.app.kokonut.email.email;

import com.app.kokonut.email.email.dto.EmailDetailDto;
import com.app.kokonut.email.email.dto.EmailListDto;
import com.app.kokonut.email.email.dto.entity.QEmail;
import com.app.kokonut.email.email.entity.Email;
import com.app.kokonut.email.emailGroup.entity.QEmailGroup;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

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
         * SELECT `IDX`
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
                        email.idx,
                        email.receiverType,
                        email.receiverAdminIdxList,
                        email.emailGroupIdx,
                        email.title,
                        email.contents
                ));

        return query.fetchOne();
    }
}

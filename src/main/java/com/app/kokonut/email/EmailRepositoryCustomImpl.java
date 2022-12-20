package com.app.kokonut.email;

import com.app.kokonut.email.entity.Email;
import com.app.kokonut.email.dto.EmailListDto;
import com.app.kokonut.email.entity.QEmail;
import com.app.kokonut.refactor.emailGroup.entity.QEmailGroup;
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

    //이메일 발송 목록 조회
    @Override
    public Page<EmailListDto> findByEmailPage(Pageable pageable) {

        QEmail email  = QEmail.email;
        QEmailGroup emailGroup  = QEmailGroup.emailGroup;

        JPQLQuery<EmailListDto> query = from(email)
                .leftJoin(emailGroup).on(emailGroup.idx.eq(email.emailGroupIdx))
                .select(Projections.constructor(EmailListDto.class,
                        email.idx,
                        email.senderAdminIdx,
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
}

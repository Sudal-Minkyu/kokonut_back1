package com.app.kokonut.email.emailGroup;

import com.app.kokonut.email.emailGroup.dto.EmailGroupAdminInfoDto;
import com.app.kokonut.email.emailGroup.dto.EmailGroupListDto;
import com.app.kokonut.email.emailGroup.entity.EmailGroup;
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
 * Date : 2022-12-22
 * Time :
 * Remark : EmailGroupRepositoryCustom 쿼리문 선언부
 */
@Repository
public class EmailGroupRepositoryCustomImpl extends QuerydslRepositorySupport implements EmailGroupRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;

    public EmailGroupRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(EmailGroup.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    // EmailGroup 단일 조회
    // param : idx emailGroupIdx
    @Override
    public EmailGroupAdminInfoDto findEmailGroupAdminInfoByIdx(Integer idx) {
        /*
           SELECT `ADMIN_IDX_LIST`
		    FROM `email_group`
		   WHERE 1 = 1
			AND `USE_YN` = 'Y'
			AND `IDX` = #{idx}
		*/
        QEmailGroup emailGroup = QEmailGroup.emailGroup;

        JPQLQuery<EmailGroupAdminInfoDto> query = from(emailGroup)
                .where(emailGroup.idx.eq(idx),emailGroup.useYn.eq("Y"))
                .select(Projections.constructor(EmailGroupAdminInfoDto.class,
                        emailGroup.adminIdxList
                ));

        return query.fetchOne();
    }

    @Override
    public Page<EmailGroupListDto> findEmailGroupPage(Pageable pageable) {
        /* SELECT 'IDX'
                , 'ADMIN_IDX_LIST'
                , 'NAME'
                , 'DESC'
             FROM 'email_group'
            WHERE 1 = 1
              AND `USE_YN` = 'Y'
            ORDER BY `REGDATE` DESC
         */

        QEmailGroup emailGroup  = QEmailGroup.emailGroup;

        JPQLQuery<EmailGroupListDto> query = from(emailGroup)
                .where(emailGroup.useYn.eq("Y"))
                .select(Projections.constructor(EmailGroupListDto.class,
                                emailGroup.idx,
                                emailGroup.adminIdxList,
                                emailGroup.name,
                                emailGroup.desc
                ));
        query.orderBy(emailGroup.regdate.desc());

        final List<EmailGroupListDto> emailGroupListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(emailGroupListDtos, pageable, query.fetchCount());
    }

    // EmailGroupList 조회 페이징 처리를 위한 pageable를 포함하지 않는다.
    @Override
    public List<EmailGroupListDto> findEmailGroupDetails() {
        QEmailGroup emailGroup  = QEmailGroup.emailGroup;
        JPQLQuery<EmailGroupListDto> query = from(emailGroup)
                .where(emailGroup.useYn.eq("Y"))
                .select(Projections.constructor(EmailGroupListDto.class,
                        emailGroup.idx,
                        emailGroup.adminIdxList,
                        emailGroup.name,
                        emailGroup.desc
                ));
        query.orderBy(emailGroup.regdate.desc());

        return query.fetch();
    }

}

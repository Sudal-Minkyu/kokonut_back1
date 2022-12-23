package com.app.kokonut.bizMessage.friendtalkMessage;

import com.app.kokonut.bizMessage.friendtalkMessage.dto.FriendtalkMessageInfoListDto;
import com.app.kokonut.bizMessage.friendtalkMessage.dto.FriendtalkMessageListDto;
import com.app.kokonut.bizMessage.friendtalkMessage.dto.FriendtalkMessageSearchDto;
import com.app.kokonut.refactor.company.entity.QCompany;
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
 * @author Woody
 * Date : 2022-12-20
 * Time :
 * Remark : FriendtalkMessageRepositoryCustom 쿼리문 선언부
 */
@Repository
public class FriendtalkMessageRepositoryCustomImpl extends QuerydslRepositorySupport implements FriendtalkMessageRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public FriendtalkMessageRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(FriendtalkMessage.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    @Override
    public Page<FriendtalkMessageListDto> findByFriendtalkMessagePage(FriendtalkMessageSearchDto friendtalkMessageSearchDto, int companyIdx, Pageable pageable) {

        QFriendtalkMessage friendtalkMessage  = QFriendtalkMessage.friendtalkMessage;

        JPQLQuery<FriendtalkMessageListDto> query = from(friendtalkMessage)
                .where(friendtalkMessage.companyIdx.eq(companyIdx))
                .select(Projections.constructor(FriendtalkMessageListDto.class,
                        friendtalkMessage.channelId,
                        friendtalkMessage.requestId,
                        friendtalkMessage.status,
                        friendtalkMessage.regdate
                ));

        if(friendtalkMessageSearchDto.getRequestId() != null){
            query.where(friendtalkMessage.requestId.containsIgnoreCase(friendtalkMessageSearchDto.getRequestId()));
        }

        if(friendtalkMessageSearchDto.getStatus() != null){
            query.where(friendtalkMessage.status.eq(friendtalkMessageSearchDto.getStatus()));
        }

        if(friendtalkMessageSearchDto.getStatus() != null){
            query.where(friendtalkMessage.regdate.goe(friendtalkMessageSearchDto.getStimeStart()));
        }

        if(friendtalkMessageSearchDto.getStimeEnd() != null){
            query.where(friendtalkMessage.regdate.loe(friendtalkMessageSearchDto.getStimeEnd()));
        }

        query.orderBy(friendtalkMessage.regdate.desc());

        final List<FriendtalkMessageListDto> friendtalkMessageListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(friendtalkMessageListDtos, pageable, query.fetchCount());
    }
    
    @Override
    public List<FriendtalkMessageInfoListDto> findByFriendtalkMessageInfoList(int companyIdx, String state) {

        QFriendtalkMessage friendtalkMessage = QFriendtalkMessage.friendtalkMessage;
        QCompany company = QCompany.company;

        JPQLQuery<FriendtalkMessageInfoListDto> query = from(friendtalkMessage)
                .innerJoin(company).on(company.idx.eq(companyIdx))
                .where(friendtalkMessage.companyIdx.eq(company.idx))
                .select(Projections.constructor(FriendtalkMessageInfoListDto.class,
                        friendtalkMessage.idx,
                        friendtalkMessage.requestId,
                        friendtalkMessage.transmitType,
                        friendtalkMessage.status
                ));

        if(state.equals("1")){
            // state 값이 "1"일 경우 : statue 값이 success, fail, canceled, done, stale 은 제외한다.
            query.where(friendtalkMessage.status.in("init", "processing", "reserved", "scheduled", "ready"));
        }

        return query.fetch();
    }

}
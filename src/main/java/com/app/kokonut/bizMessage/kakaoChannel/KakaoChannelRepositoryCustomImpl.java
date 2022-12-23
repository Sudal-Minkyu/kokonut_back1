package com.app.kokonut.bizMessage.kakaoChannel;

import com.app.kokonut.bizMessage.kakaoChannel.dto.KakaoChannelByChannelIdListDto;
import com.app.kokonut.bizMessage.kakaoChannel.dto.KakaoChannelListDto;
import com.app.kokonut.bizMessage.kakaoChannel.dto.KakaoChannelSearchDto;
import com.app.kokonut.bizMessage.kakaoChannel.entity.KakaoChannel;
import com.app.kokonut.bizMessage.kakaoChannel.entity.QKakaoChannel;
import com.app.kokonut.company.entity.QCompany;
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
 * Date : 2022-12-15
 * Time :
 * Remark : KakaoChannelRepositoryCustom 쿼리문 선언부
 */
@Repository
public class KakaoChannelRepositoryCustomImpl extends QuerydslRepositorySupport implements KakaoChannelRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public KakaoChannelRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(KakaoChannel.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    @Override
    public Page<KakaoChannelListDto> findByKakaoChannelPage(KakaoChannelSearchDto kakaoChannelSearchDto, int companyIdx, Pageable pageable) {

        QKakaoChannel kakaoChannel  = QKakaoChannel.kakaoChannel;
        QCompany company  = QCompany.company;

        JPQLQuery<KakaoChannelListDto> query = from(kakaoChannel)
                .innerJoin(company).on(company.idx.eq(companyIdx))
                .select(Projections.constructor(KakaoChannelListDto.class,
                        kakaoChannel.idx,
                        kakaoChannel.companyIdx,
                        kakaoChannel.channelId,
                        kakaoChannel.channelName,
                        kakaoChannel.status,
                        kakaoChannel.regdate,
                        company.companyName
                ));

        if(kakaoChannelSearchDto.getChannelName() != null){
            query.where(kakaoChannel.channelName.containsIgnoreCase(kakaoChannelSearchDto.getChannelName()));
        }

        if(kakaoChannelSearchDto.getStatus() != null){
            query.where(kakaoChannel.status.eq(kakaoChannelSearchDto.getStatus()));
        }

        if(kakaoChannelSearchDto.getStatus() != null){
            query.where(kakaoChannel.regdate.goe(kakaoChannelSearchDto.getStimeStart()));
        }

        if(kakaoChannelSearchDto.getStimeEnd() != null){
            query.where(kakaoChannel.regdate.loe(kakaoChannelSearchDto.getStimeEnd()));
        }

        query.orderBy(kakaoChannel.regdate.desc());

        final List<KakaoChannelListDto> kakaoChannelListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(kakaoChannelListDtos, pageable, query.fetchCount());
    }

    @Override
    public List<KakaoChannelByChannelIdListDto> findByKakaoChannelIdList(int companyIdx) {

        QKakaoChannel kakaoChannel = QKakaoChannel.kakaoChannel;
        QCompany company = QCompany.company;

        JPQLQuery<KakaoChannelByChannelIdListDto> query = from(kakaoChannel)
                .innerJoin(company).on(company.idx.eq(companyIdx))
                .where(kakaoChannel.companyIdx.eq(company.idx))
                .select(Projections.constructor(KakaoChannelByChannelIdListDto.class,
                        kakaoChannel.channelId
                ));

        return query.fetch();
    }





}

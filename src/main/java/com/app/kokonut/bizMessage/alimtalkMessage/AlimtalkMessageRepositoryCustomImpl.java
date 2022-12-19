package com.app.kokonut.bizMessage.alimtalkMessage;

import com.app.kokonut.bizMessage.alimtalkMessage.dto.AlimtalkMessageInfoListDto;
import com.app.kokonut.bizMessage.alimtalkMessage.dto.AlimtalkMessageListDto;
import com.app.kokonut.bizMessage.alimtalkMessage.dto.AlimtalkMessageSearchDto;
import com.app.kokonut.bizMessage.alimtalkMessage.entity.AlimtalkMessage;
import com.app.kokonut.bizMessage.alimtalkMessage.entity.QAlimtalkMessage;
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
 * Date : 2022-11-29
 * Time :
 * Remark : AlimtalkMessageRepositoryCustom 쿼리문 선언부
 */
@Repository
public class AlimtalkMessageRepositoryCustomImpl extends QuerydslRepositorySupport implements AlimtalkMessageRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public AlimtalkMessageRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(AlimtalkMessage.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    @Override
    public Page<AlimtalkMessageListDto> findByAlimtalkMessagePage(AlimtalkMessageSearchDto alimtalkMessageSearchDto, int companyIdx, Pageable pageable) {

        QAlimtalkMessage alimtalkMessage  = QAlimtalkMessage.alimtalkMessage;

        JPQLQuery<AlimtalkMessageListDto> query = from(alimtalkMessage)
                .where(alimtalkMessage.companyIdx.eq(companyIdx))
                .select(Projections.constructor(AlimtalkMessageListDto.class,
                        alimtalkMessage.channelId,
                        alimtalkMessage.templateCode,
                        alimtalkMessage.requestId,
                        alimtalkMessage.status,
                        alimtalkMessage.regdate
                ));

        if(alimtalkMessageSearchDto.getTemplateCode() != null){
            query.where(alimtalkMessage.templateCode.containsIgnoreCase(alimtalkMessageSearchDto.getTemplateCode()));
        }

        if(alimtalkMessageSearchDto.getStatus() != null){
            query.where(alimtalkMessage.status.eq(alimtalkMessageSearchDto.getStatus()));
        }

        if(alimtalkMessageSearchDto.getStatus() != null){
            query.where(alimtalkMessage.regdate.goe(alimtalkMessageSearchDto.getStimeStart()));
        }

        if(alimtalkMessageSearchDto.getStimeEnd() != null){
            query.where(alimtalkMessage.regdate.loe(alimtalkMessageSearchDto.getStimeEnd()));
        }

        query.orderBy(alimtalkMessage.regdate.desc());

        final List<AlimtalkMessageListDto> alimtalkMessageListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(alimtalkMessageListDtos, pageable, query.fetchCount());
    }

    @Override
    public List<AlimtalkMessageInfoListDto> findByAlimtalkMessageInfoList(int companyIdx, String state) {

        QAlimtalkMessage alimtalkMessage = QAlimtalkMessage.alimtalkMessage;
        QCompany company = QCompany.company;

        JPQLQuery<AlimtalkMessageInfoListDto> query = from(alimtalkMessage)
                .innerJoin(company).on(company.idx.eq(companyIdx))
                .where(alimtalkMessage.companyIdx.eq(company.idx))
                .select(Projections.constructor(AlimtalkMessageInfoListDto.class,
                        alimtalkMessage.idx,
                        alimtalkMessage.requestId,
                        alimtalkMessage.transmitType,
                        alimtalkMessage.status
                ));

        if(state.equals("1")){
            query.where(alimtalkMessage.status.eq("success").or(alimtalkMessage.status.eq("REGISTER")).or(alimtalkMessage.status.eq("INSPECT")));
        }

        return query.fetch();
    }



}

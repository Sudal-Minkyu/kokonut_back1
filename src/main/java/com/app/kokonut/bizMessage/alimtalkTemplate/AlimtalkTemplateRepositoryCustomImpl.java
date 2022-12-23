package com.app.kokonut.bizMessage.alimtalkTemplate;

import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkMessageTemplateInfoListDto;
import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkTemplateInfoListDto;
import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkTemplateListDto;
import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkTemplateSearchDto;
import com.app.kokonut.company.QCompany;
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
 * Remark : AlimtalkTemplateRepositoryCustomImpl 쿼리문 선언부
 */
@Repository
public class AlimtalkTemplateRepositoryCustomImpl extends QuerydslRepositorySupport implements AlimtalkTemplateRepositoryCustom {

    public final JpaResultMapper jpaResultMapper;

    public AlimtalkTemplateRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(AlimtalkTemplate.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    @Override
    public Page<AlimtalkTemplateListDto> findByAlimtalkTemplatePage(AlimtalkTemplateSearchDto alimtalkTemplateSearchDto, int companyIdx, Pageable pageable) {

        QAlimtalkTemplate alimtalkTemplate  = QAlimtalkTemplate.alimtalkTemplate;

        JPQLQuery<AlimtalkTemplateListDto> query = from(alimtalkTemplate)
                .where(alimtalkTemplate.companyIdx.eq(companyIdx))
                .select(Projections.constructor(AlimtalkTemplateListDto.class,
                        alimtalkTemplate.channelId,
                        alimtalkTemplate.templateCode,
                        alimtalkTemplate.templateName,
                        alimtalkTemplate.regdate,
                        alimtalkTemplate.status
                ));

        if(alimtalkTemplateSearchDto.getTemplateName() != null){
            query.where(alimtalkTemplate.templateName.containsIgnoreCase(alimtalkTemplateSearchDto.getTemplateName()));
        }

        if(alimtalkTemplateSearchDto.getStatus() != null){
            query.where(alimtalkTemplate.status.eq(alimtalkTemplateSearchDto.getStatus()));
        }

        if(alimtalkTemplateSearchDto.getStatus() != null){
            query.where(alimtalkTemplate.regdate.goe(alimtalkTemplateSearchDto.getStimeStart()));
        }

        if(alimtalkTemplateSearchDto.getStimeEnd() != null){
            query.where(alimtalkTemplate.regdate.loe(alimtalkTemplateSearchDto.getStimeEnd()));
        }

        query.orderBy(alimtalkTemplate.regdate.desc());

        final List<AlimtalkTemplateListDto> alimtalkTemplateListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(alimtalkTemplateListDtos, pageable, query.fetchCount());
    }

    @Override
    public List<AlimtalkTemplateInfoListDto> findByAlimtalkTemplateInfoList(int companyIdx, String channelId, String state) {

        QAlimtalkTemplate alimtalkTemplate = QAlimtalkTemplate.alimtalkTemplate;
        QCompany company = QCompany.company;

        JPQLQuery<AlimtalkTemplateInfoListDto> query = from(alimtalkTemplate)
                .innerJoin(company).on(company.idx.eq(companyIdx))
                .where(alimtalkTemplate.channelId.eq(channelId).and(alimtalkTemplate.companyIdx.eq(company.idx)))
                .select(Projections.constructor(AlimtalkTemplateInfoListDto.class,
                        alimtalkTemplate.templateCode,
                        alimtalkTemplate.status
                ));

        if(state.equals("1")){
            query.where(alimtalkTemplate.status.eq("ACCEPT").or(alimtalkTemplate.status.eq("REGISTER")).or(alimtalkTemplate.status.eq("INSPECT")));
        }

        return query.fetch();
    }

    @Override
    public List<AlimtalkMessageTemplateInfoListDto> findByAlimtalkMessageTemplateInfoList(int companyIdx, String channelId) {

        QAlimtalkTemplate alimtalkTemplate = QAlimtalkTemplate.alimtalkTemplate;
        QCompany company = QCompany.company;

        JPQLQuery<AlimtalkMessageTemplateInfoListDto> query = from(alimtalkTemplate)
                .innerJoin(company).on(company.idx.eq(companyIdx))
                .where(alimtalkTemplate.channelId.eq(channelId).and(alimtalkTemplate.companyIdx.eq(company.idx)))
                .select(Projections.constructor(AlimtalkMessageTemplateInfoListDto.class,
                        alimtalkTemplate.templateCode,
                        alimtalkTemplate.messageType,
                        alimtalkTemplate.extraContent,
                        alimtalkTemplate.adContent,
                        alimtalkTemplate.emphasizeType,
                        alimtalkTemplate.emphasizeTitle,
                        alimtalkTemplate.emphasizeSubTitle
                ));

        return query.fetch();
    }



}

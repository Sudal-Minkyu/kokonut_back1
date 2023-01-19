package com.app.kokonut.faq;

import com.app.kokonut.faq.dtos.FaqAnswerListDto;
import com.app.kokonut.faq.dtos.FaqDetailDto;
import com.app.kokonut.faq.dtos.FaqListDto;
import com.app.kokonut.faq.dtos.FaqSearchDto;
import com.app.kokonut.faq.entity.QFaq;
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
 * Date : 2022-12-27
 * Time :
 * Remark : FaqRepositoryCustom 쿼리문 선언부
 */
@Repository
public class FaqRepositoryCustomImpl extends QuerydslRepositorySupport implements FaqRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;

    public FaqRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(Faq.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    @Override
    public Page<FaqListDto> findFaqPage(FaqSearchDto faqSearchDto, Pageable pageable) {
        /*
           SELECT `IDX`
	            , `QUESTION`
	            , `ANSWER`
	            , `TYPE`
	            , `REGDATE`
	            , `VIEW_COUNT`
             FROM `faq`
            WHERE 1 = 1
	          AND `TYPE` = #{type}
	          AND `REGDATE` BETWEEN #{stimeStart} AND #{stimeEnd}
	          AND ( `QUESTION` LIKE CONCAT('%', #{searchText}, '%') )
            ORDER BY `REGDATE` DESC
         */
        QFaq faq  = QFaq.faq;

        JPQLQuery<FaqListDto> query = from(faq)
                .select(Projections.constructor(FaqListDto.class,
                        faq.idx,
                        faq.question,
                        faq.answer,
                        faq.type,
                        faq.regdate,
                        faq.viewCount
                ));
        // 조건에 따른 where 절 추가
        if(faqSearchDto.getType() != null){
            query.where(faq.type.eq(faqSearchDto.getType()));
        }
        if((faqSearchDto.getStimeStart() != null) && (faqSearchDto.getStimeEnd() !=null)){
            query.where(faq.regdate.between(faqSearchDto.getStimeStart(), faqSearchDto.getStimeEnd()));
        }
        if(faqSearchDto.getSearchText() != null){
            query.where(faq.question.contains(faqSearchDto.getSearchText()));
        }
        query.orderBy(faq.regdate.desc());

        final List<FaqListDto> FaqListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(FaqListDtos, pageable, query.fetchCount());
    }
    @Override
    public Page<FaqAnswerListDto> findFaqAnswerPage(Pageable pageable) {
        /*
           SELECT `QUESTION`
	            , `ANSWER`
             FROM `faq`
            WHERE 1 = 1
	          AND STATE = #{state}
            ORDER BY `REGDATE` DESC
         */
        QFaq faq  = QFaq.faq;
        JPQLQuery<FaqAnswerListDto> query = from(faq)
                .select(Projections.constructor(FaqAnswerListDto.class,
                        faq.question,
                        faq.answer
                ));
        // 조건에 따른 where 절 추가
        query.where(faq.state.eq(1));
        query.orderBy(faq.regdate.desc());

        final List<FaqAnswerListDto> FaqAnswerListDto = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(FaqAnswerListDto, pageable, query.fetchCount());
    }
    @Override
    public FaqDetailDto findFaqByIdx(Integer idx) {
        /*
           SELECT `IDX`
                , `ADMIN_IDX`
	            , `QUESTION`
	            , `ANSWER`
	            , `TYPE`
	            , `REGISTER_NAME`
	            , `REGDATE`
	            , `MODIFIER_IDX`
	            , `MODIFIER_NAME`
	            , `MODIFY_DATE`
	            , `VIEW_COUNT`
             FROM `faq`
            WHERE 1 = 1
	          AND `IDX` = #{idx}
         */
        QFaq faq  = QFaq.faq;
        JPQLQuery<FaqDetailDto> query = from(faq)
                .select(Projections.constructor(FaqDetailDto.class,
                        faq.idx,
                        faq.adminId,
                        faq.question,
                        faq.answer,
                        faq.type,
                        faq.registerName,
                        faq.regdate,
                        faq.modifierIdx,
                        faq.modifierName,
                        faq.viewCount
                ));
        query.where(faq.idx.eq(idx));
        return query.fetchOne();
    }
}

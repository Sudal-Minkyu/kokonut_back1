package com.app.kokonut.collectInformation;

import com.app.kokonut.collectInformation.dto.CollectInfoDetailDto;
import com.app.kokonut.collectInformation.dto.CollectInfoListDto;
import com.app.kokonut.collectInformation.dto.CollectInfoSearchDto;
import com.app.kokonut.collectInformation.entity.CollectInformation;
import com.app.kokonut.collectInformation.entity.QCollectInformation;
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
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : CollectInformationRepository 개인정보 처리방침 - 개인정보 수집 및 이용 안내 쿼리 선언부
 */
@Repository
public class CollectInformationRepositoryCustomImpl extends QuerydslRepositorySupport implements CollectInformationRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;

    public CollectInformationRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(CollectInformation.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    @Override
    public Page<CollectInfoListDto> findCollectInfoPage(Long companyIdx, CollectInfoSearchDto collectInfoSearchDto, Pageable pageable) {
        /*
           SELECT `IDX`
	            , `TITLE`
	            , `REGISTER_NAME`
	            , `REGDATE`
             FROM `collect_information`
            WHERE 1 = 1
	          AND ( `TITLE` LIKE CONCAT('%', #{searchText}, '%') )
	          AND `COMPANY_IDX` = #{companyIdx}
            ORDER BY `REGDATE` DESC
         */
        QCollectInformation collectInfo = QCollectInformation.collectInformation;
        JPQLQuery<CollectInfoListDto> query = from(collectInfo)
                .select(Projections.constructor(CollectInfoListDto.class,
                        collectInfo.idx,
                        collectInfo.title,
                        collectInfo.insert_email,
                        collectInfo.insert_date
                        ));
        query.where(collectInfo.companyIdx.eq(companyIdx));
        // 조건에 따른 where 절 추가
        if(collectInfoSearchDto.getSearchText() != null){
            query.where(collectInfo.title.contains(collectInfoSearchDto.getSearchText()));
        }
        query.orderBy(collectInfo.insert_date.desc());

        final List<CollectInfoListDto> collectInfoListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(collectInfoListDtos, pageable, query.fetchCount());
    }

    @Override
    public CollectInfoDetailDto findCollectInfoByIdx(Long idx) {
        /*
           SELECT `IDX`
                , `TITLE`
	            , `CONTENT`
             FROM `collect_information`
            WHERE 1 = 1
	          AND `IDX` = #{idx}
         */
        QCollectInformation collectInfo = QCollectInformation.collectInformation;
        JPQLQuery<CollectInfoDetailDto> query = from(collectInfo)
                .select(Projections.constructor(CollectInfoDetailDto.class,
                        collectInfo.idx,
                        collectInfo.title,
                        collectInfo.content
                ));
        query.where(collectInfo.idx.eq(idx));
        return query.fetchOne();
    }

//    @Override
//    public List findCollectInfoIdxByCompayId(Integer companyIdx) {
//        /*
//           SELECT `IDX`
//             FROM `collect_information`
//            WHERE 1 = 1
//	          AND `COMPANY_IDX` = #{companyIdx}
//         */
//        QCollectInformation collectInfo = QCollectInformation.collectInformation;
//        JPQLQuery<Integer> query = from(collectInfo)
//                .select(Projections.constructor(Integer.class,
//                        collectInfo.idx
//                ));
//        query.where(collectInfo.companyIdx.eq(companyIdx));
//        return query.fetchAll().fetch();
//    }
}

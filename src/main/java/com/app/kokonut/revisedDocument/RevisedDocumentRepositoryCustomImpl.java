package com.app.kokonut.revisedDocument;

import com.app.kokonut.revisedDocument.dtos.RevDocListDto;
import com.app.kokonut.revisedDocument.dtos.RevDocSearchDto;
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
 * Remark : RevisedDocumentRepositoryCustom 개인정보 처리방침 - 개정 문서 쿼리 선언부
 */
@Repository
public class RevisedDocumentRepositoryCustomImpl extends QuerydslRepositorySupport implements RevisedDocumentRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;
    public RevisedDocumentRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(RevisedDocument.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    @Override
    public Page<RevDocListDto> findRevDocPage(Long companyId, RevDocSearchDto revDocSearchDto, Pageable pageable) {
       /*
        * SELECT A.`IDX`
        *      , A.`ENFORCE_START_DATE`
	    *      , A.`ENFORCE_END_DATE`
		*      , A.`REGISTER_NAME`
	    *      , A.`REGDATE`
	    *      , B.`CF_ORIGINAL_FILENAME`
	    *   FROM `revised_document` A
	   LEFT JOIN `revise_doc_file` B
		      ON A.`IDX` = B.`REVISED_DOCUMENT_IDX`
	  WHERE 1 = 1
	    AND A.`COMPANY_IDX` = #{companyId}
		AND A.`REGDATE` BETWEEN #{stimeStart} AND #{stimeEnd}
	  ORDER BY A.`REGDATE` DESC
	    *
	    */
        QRevisedDocument doc  = QRevisedDocument.revisedDocument;
        QRevisedDocumentFile file  = QRevisedDocumentFile.revisedDocumentFile;

        JPQLQuery<RevDocListDto> query = from(doc)
                //.leftJoin(file).on(doc.idx.eq(file.revisedDocumentIdx))
                .select(Projections.constructor(RevDocListDto.class,
                        doc.idx,
                        doc.enforceStartDate,
                        doc.enforceEndDate,
                        doc.registerName,
                        doc.regdate
                        //, file.cfOriginalFilename
                ));
        query.where(doc.companyId.eq(companyId),
                doc.regdate.between(revDocSearchDto.getStimeStart(), revDocSearchDto.getStimeEnd())
        );

        query.orderBy(doc.regdate.desc());
        final List<RevDocListDto> revDocListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(revDocListDtos, pageable, query.fetchCount());
    }
}

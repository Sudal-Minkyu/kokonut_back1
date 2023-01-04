package com.app.kokonut.revisedDocument;

import com.app.kokonut.revisedDocument.dto.RevDocListDto;
import com.app.kokonut.revisedDocument.dto.RevDocSearchDto;
import com.app.kokonut.revisedDocument.entity.RevisedDocument;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : RevisedDocumentRepository 개인정보 처리방침 - 개정 문서 쿼리 선언부
 */
@Repository
public class RevisedDocumentRepositoryCustomImpl extends QuerydslRepositorySupport implements RevisedDocumentRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;
    public RevisedDocumentRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(RevisedDocument.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    @Override
    public Page<RevDocListDto> findRevDocPage(Integer companyIdx, RevDocSearchDto revDocSearchDto, Pageable pageable) {
       /*
        * SELECT A.`IDX`
        *      , A.`ENFORCE_START_DATE`
	    *      , A.`ENFORCE_END_DATE`
		*      , A.`REGISTER_NAME`
	    *      , A.`REGDATE`
	    *      , B.`IDX` AS `FILE_IDX`,
	    *      , B.`CF_ORIGINAL_FILENAME`
	    *   FROM `revised_document` A
	   LEFT JOIN `rev_doc_file` B
		      ON A.`IDX` = B.`REV_DOC_IDX`
	  WHERE 1 = 1
	    AND A.`COMPANY_IDX` = #{companyIdx}
		AND A.`REGDATE` BETWEEN #{stimeStart} AND #{stimeEnd}
	  ORDER BY A.`REGDATE` DESC
	    *
	    */

        return null;
    }
}

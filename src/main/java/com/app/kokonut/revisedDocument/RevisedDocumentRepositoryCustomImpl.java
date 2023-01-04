package com.app.kokonut.revisedDocument;

import com.app.kokonut.revisedDocument.entity.RevisedDocument;
import org.qlrm.mapper.JpaResultMapper;
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
}

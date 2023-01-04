package com.app.kokonut.revisedDocument;

import com.app.kokonut.qna.dto.QnaListDto;
import com.app.kokonut.qna.dto.QnaSearchDto;
import com.app.kokonut.revisedDocument.dto.RevDocListDto;
import com.app.kokonut.revisedDocument.dto.RevDocSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : RevisedDocumentRepository 개인정보 처리방침 - 개정문서 기존 쿼리 호출
 */
public interface RevisedDocumentRepositoryCustom {
    // 개정문서 목록 조회
    // 기존 코코넛 서비스 메서드 SelectRevisedDocumentList
    Page<RevDocListDto> findRevDocPage(Integer companyIdx, RevDocSearchDto revDocSearchDto, Pageable pageable);
}
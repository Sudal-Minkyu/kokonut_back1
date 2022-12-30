package com.app.kokonut.qna;

import com.app.kokonut.qna.dto.QnaListDto;
import com.app.kokonut.qna.dto.QnaDetailDto;
import com.app.kokonut.qna.dto.QnaSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author joy
 * Date : 2022-12-27
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 QnaDao 쿼리호출
 */
public interface QnaRepositoryCustom {
    // qna 목록 조회
    // 기존 코코넛 서비스 메서드 SelectQnaList
    Page<QnaListDto> findQnaPage(QnaSearchDto qnaSearchDto, Pageable pageable);
    QnaDetailDto findQnaByIdx(Integer idx);
}

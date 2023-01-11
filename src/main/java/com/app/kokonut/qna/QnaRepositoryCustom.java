package com.app.kokonut.qna;

import com.app.kokonut.qna.dto.QnaListDto;
import com.app.kokonut.qna.dto.QnaDetailDto;
import com.app.kokonut.qna.dto.QnaSchedulerDto;
import com.app.kokonut.qna.dto.QnaSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author joy
 * Date : 2022-12-27
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 QnaDao 쿼리호출
 */
public interface QnaRepositoryCustom {
    // qna 목록 조회 - 기존 SelectQnaList, SelectQnaListCount
    Page<QnaListDto> findQnaPage(String userRole, QnaSearchDto qnaSearchDto, Pageable pageable);

    // qna 내용 조회 - 기존 SelectQnaByIdx
    QnaDetailDto findQnaByIdx(Integer idx);

    // qna 답변 지연 게시글 조회 - 기존 SelectNonAnsweredQnaList
    List<QnaSchedulerDto> findNoneAnswerQnaByRegDate(LocalDateTime compareDate);

    // qna 등록 - 기존 InsertQna
    // qna 수정 - 기존 UpdateQna
    // qna 삭제 - 기존 DeleteQnaByIdx
    // qna 전체 삭제 - 기존 DeleteByCompanyIdx
}

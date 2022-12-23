package com.app.kokonut.email.email;

import com.app.kokonut.email.email.dto.EmailDetailDto;
import com.app.kokonut.email.email.dto.EmailListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author joy
 * Date : 2022-12-19
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 Email Sql 쿼리호출
 */
public interface EmailRepositoryCustom {

    // 이메일 목록 조회 -> 기존의 코코넛 메서트 : SelectEmailList
    Page<EmailListDto> findByEmailPage(Pageable pageable);

    // 이메일 상세 조회 -> 기존 코코넛 메서드 : SelectEmailByIdx
    EmailDetailDto findEmailByIdx(Integer idx);

    // 이메일 발송 내역 저장 -> 기존 코코넛 메서드 : InsertEmail
    Integer saveEmail(EmailDetailDto emailDetailDto);
}
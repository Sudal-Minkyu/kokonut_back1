package com.app.kokonut.email;

import com.app.kokonut.email.dto.EmailListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * @author joy
 * Date : 2022-12-19
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 Email Sql 쿼리호출
 */
public interface EmailRepositoryCustom {

    // 이메일 발송 목록 호출 -> 기존의 코코넛 메서트 : SelectEmailList
    Page<EmailListDto> findByEmailPage(Pageable pageable);
}
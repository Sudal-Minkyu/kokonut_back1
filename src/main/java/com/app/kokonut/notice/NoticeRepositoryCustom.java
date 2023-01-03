package com.app.kokonut.notice;

import com.app.kokonut.notice.dto.NoticeContentListDto;
import com.app.kokonut.notice.dto.NoticeDetailDto;
import com.app.kokonut.notice.dto.NoticeListDto;
import com.app.kokonut.notice.dto.NoticeSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author joy
 * Date : 2022-12-27
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 NoticeDao 쿼리호출
 */
public interface NoticeRepositoryCustom {
    Page<NoticeListDto> findNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable);

    Page<NoticeContentListDto> findNoticeContentPage(Pageable pageable);

    NoticeDetailDto findNoticeByIdx(Integer idx);

}

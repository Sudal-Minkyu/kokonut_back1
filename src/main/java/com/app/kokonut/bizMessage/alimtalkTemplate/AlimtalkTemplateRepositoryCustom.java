package com.app.kokonut.bizMessage.alimtalkTemplate;

import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkTemplateInfoListDto;
import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkTemplateListDto;
import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkTemplateSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Woody
 * Date : 2022-11-29
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 AlimtalkTemplate Sql 쿼리호출
 */
public interface AlimtalkTemplateRepositoryCustom {

    Page<AlimtalkTemplateListDto> findByAlimtalkTemplatePage(AlimtalkTemplateSearchDto alimtalkTemplateSearchDto, int companyIdx, Pageable pageable);

    // state = "1"일 경우 status 값 : "ACCEPT - 수락 REGISTER - 등록 INSPECT - 검수중" 만 조회
    List<AlimtalkTemplateInfoListDto> findByAlimtalkTemplateInfoList(int companyIdx, String channelId, String state);

}
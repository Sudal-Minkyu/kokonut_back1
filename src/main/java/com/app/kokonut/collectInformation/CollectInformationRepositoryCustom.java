package com.app.kokonut.collectInformation;

import com.app.kokonut.collectInformation.dto.CollectInfoDetailDto;
import com.app.kokonut.collectInformation.dto.CollectInfoListDto;
import com.app.kokonut.collectInformation.dto.CollectInfoSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : CollectInformationRepository 개인정보 처리방침 - 개인정보 수집 및 이용 안내 기존 쿼리 호출
 */
public interface CollectInformationRepositoryCustom {

    // 개인정보 처리방침 목록 조회 - 기존 SelectCollectInformationList, SelectCollectInformationListCount
    Page<CollectInfoListDto> findCollectInfoPage(CollectInfoSearchDto collectInfoSearchDto, Pageable pageable);

    // 개인정보 처리방침 상세 조회 - 기존 SelectCollectInformationByIdx
    CollectInfoDetailDto findCollectInfoByIdx(Integer idx);
}
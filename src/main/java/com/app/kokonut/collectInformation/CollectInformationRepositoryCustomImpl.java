package com.app.kokonut.collectInformation;

import com.app.kokonut.collectInformation.dto.CollectInfoDetailDto;
import com.app.kokonut.collectInformation.dto.CollectInfoListDto;
import com.app.kokonut.collectInformation.dto.CollectInfoSearchDto;
import com.app.kokonut.collectInformation.entity.CollectInformation;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : CollectInformationRepository 개인정보 처리방침 - 개인정보 수집 및 이용 쿼리 선언부
 */
@Repository
public class CollectInformationRepositoryCustomImpl extends QuerydslRepositorySupport implements CollectInformationRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;

    public CollectInformationRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(CollectInformation.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    @Override
    public Page<CollectInfoListDto> findCollectInfoPage(CollectInfoSearchDto collectInfoSearchDto, Pageable pageable) {
        return null;
    }

    @Override
    public CollectInfoDetailDto findCollectInfoByIdx(Integer idx) {
        return null;
    }
}

package com.app.kokonut.collectInformation;

import com.app.kokonut.collectInformation.entity.CollectInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : CollectInformationRepository 개인정보 처리방침 - 개인정보 수집 및 이용
 */
@Repository
public interface CollectInformationRepository extends JpaRepository<CollectInformation, Integer>, JpaSpecificationExecutor<CollectInformation>, CollectInformationRepositoryCustom {

}
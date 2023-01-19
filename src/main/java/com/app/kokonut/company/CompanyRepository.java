package com.app.kokonut.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Woody
 * Date : 2022-12-22
 * Time :
 * Remark :
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>, JpaSpecificationExecutor<Company>, CompanyRepositoryCustom {

    boolean existsByBusinessNumber(String businessNumber);

    boolean existsByCompanyCode(String companyCode);

}
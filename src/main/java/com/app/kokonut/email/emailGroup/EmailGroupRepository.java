package com.app.kokonut.email.emailGroup;

import com.app.kokonut.email.emailGroup.entity.EmailGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EmailGroupRepository extends JpaRepository<EmailGroup, Integer>, JpaSpecificationExecutor<EmailGroup>, EmailGroupRepositoryCustom {
    @Transactional
    @Modifying
    @Query("update EmailGroup e set e.adminIdxList = ?1 where e.idx is not null")
    int updateAdminIdxListByIdxNotNull(String adminIdxList);

}
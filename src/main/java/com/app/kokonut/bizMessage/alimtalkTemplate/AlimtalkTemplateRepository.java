package com.app.kokonut.bizMessage.alimtalkTemplate;

import com.app.kokonut.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface AlimtalkTemplateRepository extends JpaRepository<AlimtalkTemplate, Long>, JpaSpecificationExecutor<AlimtalkTemplate>, AlimtalkTemplateRepositoryCustom {

    @Transactional
    @Modifying
    @Query("delete from AlimtalkTemplate a where a.channelId = :channelId")
    void findByAlimtalkTemplateDelete(String channelId);

    @Query("select a from AlimtalkTemplate a where a.templateCode = :templateCode and a.channelId = :channelId and a.companyId = :companyId")
    Optional<AlimtalkTemplate> findByAlimtalkTemplate(String templateCode, String channelId, Long companyId);

}
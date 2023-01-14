package com.app.kokonut.downloadHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadHistoryRepository extends JpaRepository<DownloadHistory, Integer>, JpaSpecificationExecutor<DownloadHistory>, DownloadHistoryRepositoryCustom {

}
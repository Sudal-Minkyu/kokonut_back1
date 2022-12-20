package com.app.kokonut.refactor.downloadHistory.repository;

import com.app.kokonut.refactor.downloadHistory.entity.DownloadHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DownloadHistoryRepository extends JpaRepository<DownloadHistory, Integer>, JpaSpecificationExecutor<DownloadHistory> {

}
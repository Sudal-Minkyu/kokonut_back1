package com.app.kokonut.refactor.totalDBDownload.repository;

import com.app.kokonut.refactor.totalDBDownload.entity.TotalDbDownload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TotalDbDownloadRepository extends JpaRepository<TotalDbDownload, Integer>, JpaSpecificationExecutor<TotalDbDownload> {

}
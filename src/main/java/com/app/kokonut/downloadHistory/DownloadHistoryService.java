package com.app.kokonut.downloadHistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Woody
 * Date : 2023-01-13
 * Time :
 * Remark : DownloadHistory Service
 */
@Service
public class DownloadHistoryService {

    private DownloadHistoryRepository downloadHistoryRepository;

    @Autowired
    public DownloadHistoryService(DownloadHistoryRepository downloadHistoryRepository){
        this.downloadHistoryRepository = downloadHistoryRepository;
    }



}

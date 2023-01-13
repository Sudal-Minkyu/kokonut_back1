package com.app.kokonut.downloadHistory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Woody
 * Date : 2023-01-13
 * Time :
 * Remark : 다운로드 로그기록 관련 API RestController
 */
@Slf4j
@RequestMapping("/api/DownloadHistory")
@RestController
public class DownloadHistoryRestController {

    private final DownloadHistoryService downloadHistoryService;

    @Autowired
    public DownloadHistoryRestController(DownloadHistoryService downloadHistoryService){
        this.downloadHistoryService = downloadHistoryService;
    }


}

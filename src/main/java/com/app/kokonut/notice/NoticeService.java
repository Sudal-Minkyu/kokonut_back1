package com.app.kokonut.notice;

import org.springframework.stereotype.Service;


@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

}

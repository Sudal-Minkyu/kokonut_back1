package com.app.kokonut.notice;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "")
@Validated
@RestController
@RequestMapping("/api/Notice")
public class NoticeRestController {
    // 기존 코코넛 SystemNoticeController 컨트롤러 리팩토링
    // 기존 url : /system/notice , 변경 url : /api/Notice

    private final NoticeService noticeService;

    public NoticeRestController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

}

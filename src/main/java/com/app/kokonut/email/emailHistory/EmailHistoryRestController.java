package com.app.kokonut.email.emailHistory;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Api(tags = "")
@Validated
@RestController
@RequestMapping("/api/EmailHistory")
public class EmailHistoryRestController {


    private final EmailHistoryService emailHistoryService;

    @Autowired
    public EmailHistoryRestController(EmailHistoryService emailHistoryService) {
        this.emailHistoryService = emailHistoryService;
    }
}
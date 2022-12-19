package com.app.kokonut.bizMessage.alimtalkMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/AlimtalkMessage")
public class AlimtalkMessageRestController {

    private final AlimtalkMessageService alimtalkMessageService;

    @Autowired
    public AlimtalkMessageRestController(AlimtalkMessageService alimtalkMessageService) {
        this.alimtalkMessageService = alimtalkMessageService;
    }






}

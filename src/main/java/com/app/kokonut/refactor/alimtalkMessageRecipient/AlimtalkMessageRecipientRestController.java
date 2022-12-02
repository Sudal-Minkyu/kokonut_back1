package com.app.kokonut.refactor.alimtalkMessageRecipient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/AlimtalkMessageRecipient")
public class AlimtalkMessageRecipientRestController {

    private final AlimtalkMessageRecipientService alimtalkMessageRecipientService;

    @Autowired
    public AlimtalkMessageRecipientRestController(AlimtalkMessageRecipientService alimtalkMessageRecipientService) {
        this.alimtalkMessageRecipientService = alimtalkMessageRecipientService;
    }




}

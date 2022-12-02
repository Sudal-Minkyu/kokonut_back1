package com.app.kokonut.refactor.alimtalkTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/AlimtalkTemplate")
public class AlimtalkTemplateRestController {

    private AlimtalkTemplateService alimtalkTemplateService;

    @Autowired
    public AlimtalkTemplateRestController(AlimtalkTemplateService alimtalkTemplateService) {
        this.alimtalkTemplateService = alimtalkTemplateService;
    }

}

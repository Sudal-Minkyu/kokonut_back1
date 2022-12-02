package com.app.kokonut.refactor.alimtalkTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlimtalkTemplateService {

    private AlimtalkTemplateRepository alimtalkTemplateRepository;

    @Autowired
    public AlimtalkTemplateService(AlimtalkTemplateRepository alimtalkTemplateRepository) {
        this.alimtalkTemplateRepository = alimtalkTemplateRepository;
    }

}

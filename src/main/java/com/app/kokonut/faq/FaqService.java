package com.app.kokonut.faq;

import org.springframework.stereotype.Service;

@Service
public class FaqService {
    private final FaqRepository faqRepository;

    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }
}

package com.app.kokonut.faq;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "")
@Validated
@RestController
@RequestMapping("/api/Faq")
public class FaqRestController {
    // 기존 코코넛 SystemFaqController 컨트롤러 리팩토링
    // 기존 url : /system/faq , 변경 url : /api/Faq
    // 기존 코코넛 FaqController 컨트롤러 리팩토링
    // 기존 url : /faq, 변경 url : /api/Faq

    private final FaqService faqService;

    public FaqRestController(FaqService faqService) {
        this.faqService = faqService;
    }
}

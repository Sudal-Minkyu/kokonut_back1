package com.app.kokonut.qna;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "")
@Validated
@RestController
@RequestMapping("/api/Qna")
public class QnaRestController {
    // 기존 코코넛 SystemQnaController 컨트롤러 리팩토링
    // 기존 url : /system/qna , 변경 url : /api/Qna

    // 기존 코코넛 MemberQnaController 컨트롤러 리팩토링
    // 기존 url : /member/qna" , 변경 url : /api/Qna

    private final QnaService qnaService;

    public QnaRestController(QnaService qnaService) {
        this.qnaService = qnaService;
    }
}

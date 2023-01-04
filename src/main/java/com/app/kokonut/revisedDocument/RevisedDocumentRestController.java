package com.app.kokonut.revisedDocument;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : 개인정보 처리방침 - 개정문서 컨트롤러
 */
@Validated
@RestController
@RequestMapping("/api/RevisedDocument")
// AS-IS MemberRevisedDocumentController 리팩토링, url /member/revisedDocument -> /api/RevisedDocument
public class RevisedDocumentRestController {
    private final RevisedDocumentService revisedDocumentService;

    public RevisedDocumentRestController(RevisedDocumentService revisedDocumentService) {
        this.revisedDocumentService = revisedDocumentService;
    }
}

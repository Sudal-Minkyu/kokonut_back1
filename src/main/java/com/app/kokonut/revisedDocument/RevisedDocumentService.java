package com.app.kokonut.revisedDocument;

import com.app.kokonut.woody.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : RevisedDocumentService 개인정보 처리방침 - 개정문서 서비스
 */
@Slf4j
@Service
public class RevisedDocumentService {
    private final AjaxResponse res = new AjaxResponse();
    private final HashMap<String, Object> data = new HashMap<>();

    private final RevisedDocumentRepository revisedDocumentRepository;

    public RevisedDocumentService(RevisedDocumentRepository revisedDocumentRepository) {
        this.revisedDocumentRepository = revisedDocumentRepository;
    }

}

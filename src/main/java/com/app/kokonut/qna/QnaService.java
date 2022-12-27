package com.app.kokonut.qna;

import org.springframework.stereotype.Service;


@Service
public class QnaService {
    private final QnaRepository qnaRepository;

    public QnaService(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }
}

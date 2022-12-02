package com.app.kokonut.refactor.qna.service;

import com.app.kokonut.refactor.qna.dto.QnaDTO;
import com.app.kokonut.refactor.qna.entity.Qna;
import com.app.kokonut.refactor.qna.repository.QnaRepository;
import com.app.kokonut.refactor.qna.vo.QnaQueryVO;
import com.app.kokonut.refactor.qna.vo.QnaUpdateVO;
import com.app.kokonut.refactor.qna.vo.QnaVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class QnaService {

    @Autowired
    private QnaRepository qnaRepository;

    public Integer save(QnaVO vO) {
        Qna bean = new Qna();
        BeanUtils.copyProperties(vO, bean);
        bean = qnaRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        qnaRepository.deleteById(id);
    }

    public void update(Integer id, QnaUpdateVO vO) {
        Qna bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        qnaRepository.save(bean);
    }

    public QnaDTO getById(Integer id) {
        Qna original = requireOne(id);
        return toDTO(original);
    }

    public Page<QnaDTO> query(QnaQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private QnaDTO toDTO(Qna original) {
        QnaDTO bean = new QnaDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Qna requireOne(Integer id) {
        return qnaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

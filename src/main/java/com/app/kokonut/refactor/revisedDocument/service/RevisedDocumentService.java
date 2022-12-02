package com.app.kokonut.refactor.revisedDocument.service;

import com.app.kokonut.refactor.revisedDocument.dto.RevisedDocumentDTO;
import com.app.kokonut.refactor.revisedDocument.vo.RevisedDocumentVO;
import com.app.kokonut.refactor.revisedDocument.entity.RevisedDocument;
import com.app.kokonut.refactor.revisedDocument.repository.RevisedDocumentRepository;
import com.app.kokonut.refactor.revisedDocument.vo.RevisedDocumentQueryVO;
import com.app.kokonut.refactor.revisedDocument.vo.RevisedDocumentUpdateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RevisedDocumentService {

    @Autowired
    private RevisedDocumentRepository revisedDocumentRepository;

    public Integer save(RevisedDocumentVO vO) {
        RevisedDocument bean = new RevisedDocument();
        BeanUtils.copyProperties(vO, bean);
        bean = revisedDocumentRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        revisedDocumentRepository.deleteById(id);
    }

    public void update(Integer id, RevisedDocumentUpdateVO vO) {
        RevisedDocument bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        revisedDocumentRepository.save(bean);
    }

    public RevisedDocumentDTO getById(Integer id) {
        RevisedDocument original = requireOne(id);
        return toDTO(original);
    }

    public Page<RevisedDocumentDTO> query(RevisedDocumentQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private RevisedDocumentDTO toDTO(RevisedDocument original) {
        RevisedDocumentDTO bean = new RevisedDocumentDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private RevisedDocument requireOne(Integer id) {
        return revisedDocumentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

package com.app.kokonut.faq.service;

import com.app.kokonut.faq.dto.FaqDTO;
import com.app.kokonut.faq.entity.Faq;
import com.app.kokonut.faq.repository.FaqRepository;
import com.app.kokonut.faq.vo.FaqQueryVO;
import com.app.kokonut.faq.vo.FaqUpdateVO;
import com.app.kokonut.faq.vo.FaqVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    public Integer save(FaqVO vO) {
        Faq bean = new Faq();
        BeanUtils.copyProperties(vO, bean);
        bean = faqRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        faqRepository.deleteById(id);
    }

    public void update(Integer id, FaqUpdateVO vO) {
        Faq bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        faqRepository.save(bean);
    }

    public FaqDTO getById(Integer id) {
        Faq original = requireOne(id);
        return toDTO(original);
    }

    public Page<FaqDTO> query(FaqQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private FaqDTO toDTO(Faq original) {
        FaqDTO bean = new FaqDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Faq requireOne(Integer id) {
        return faqRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

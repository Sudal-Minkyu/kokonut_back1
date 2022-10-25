package com.app.kokonut.alimtalkTemplate.service;

import com.app.kokonut.alimtalkTemplate.dto.AlimtalkTemplateDTO;
import com.app.kokonut.alimtalkTemplate.entity.AlimtalkTemplate;
import com.app.kokonut.alimtalkTemplate.repository.AlimtalkTemplateRepository;
import com.app.kokonut.alimtalkTemplate.vo.AlimtalkTemplateQueryVO;
import com.app.kokonut.alimtalkTemplate.vo.AlimtalkTemplateUpdateVO;
import com.app.kokonut.alimtalkTemplate.vo.AlimtalkTemplateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AlimtalkTemplateService {

    @Autowired
    private AlimtalkTemplateRepository alimtalkTemplateRepository;

    public Integer save(AlimtalkTemplateVO vO) {
        AlimtalkTemplate bean = new AlimtalkTemplate();
        BeanUtils.copyProperties(vO, bean);
        bean = alimtalkTemplateRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        alimtalkTemplateRepository.deleteById(id);
    }

    public void update(Integer id, AlimtalkTemplateUpdateVO vO) {
        AlimtalkTemplate bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        alimtalkTemplateRepository.save(bean);
    }

    public AlimtalkTemplateDTO getById(Integer id) {
        AlimtalkTemplate original = requireOne(id);
        return toDTO(original);
    }

    public Page<AlimtalkTemplateDTO> query(AlimtalkTemplateQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AlimtalkTemplateDTO toDTO(AlimtalkTemplate original) {
        AlimtalkTemplateDTO bean = new AlimtalkTemplateDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AlimtalkTemplate requireOne(Integer id) {
        return alimtalkTemplateRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

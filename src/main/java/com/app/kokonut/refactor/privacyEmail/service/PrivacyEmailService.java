package com.app.kokonut.refactor.privacyEmail.service;

import com.app.kokonut.refactor.privacyEmail.dto.PrivacyEmailDTO;
import com.app.kokonut.refactor.privacyEmail.entity.PrivacyEmail;
import com.app.kokonut.refactor.privacyEmail.repository.PrivacyEmailRepository;
import com.app.kokonut.refactor.privacyEmail.vo.PrivacyEmailQueryVO;
import com.app.kokonut.refactor.privacyEmail.vo.PrivacyEmailUpdateVO;
import com.app.kokonut.refactor.privacyEmail.vo.PrivacyEmailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PrivacyEmailService {

    @Autowired
    private PrivacyEmailRepository privacyEmailRepository;

    public Integer save(PrivacyEmailVO vO) {
        PrivacyEmail bean = new PrivacyEmail();
        BeanUtils.copyProperties(vO, bean);
        bean = privacyEmailRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        privacyEmailRepository.deleteById(id);
    }

    public void update(Integer id, PrivacyEmailUpdateVO vO) {
        PrivacyEmail bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        privacyEmailRepository.save(bean);
    }

    public PrivacyEmailDTO getById(Integer id) {
        PrivacyEmail original = requireOne(id);
        return toDTO(original);
    }

    public Page<PrivacyEmailDTO> query(PrivacyEmailQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private PrivacyEmailDTO toDTO(PrivacyEmail original) {
        PrivacyEmailDTO bean = new PrivacyEmailDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private PrivacyEmail requireOne(Integer id) {
        return privacyEmailRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

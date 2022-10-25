package com.app.kokonut.privacyEmailHistory.service;

import com.app.kokonut.privacyEmailHistory.dto.PrivacyEmailHistoryDTO;
import com.app.kokonut.privacyEmailHistory.entity.PrivacyEmailHistory;
import com.app.kokonut.privacyEmailHistory.repository.PrivacyEmailHistoryRepository;
import com.app.kokonut.privacyEmailHistory.vo.PrivacyEmailHistoryQueryVO;
import com.app.kokonut.privacyEmailHistory.vo.PrivacyEmailHistoryUpdateVO;
import com.app.kokonut.privacyEmailHistory.vo.PrivacyEmailHistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PrivacyEmailHistoryService {

    @Autowired
    private PrivacyEmailHistoryRepository privacyEmailHistoryRepository;

    public Integer save(PrivacyEmailHistoryVO vO) {
        PrivacyEmailHistory bean = new PrivacyEmailHistory();
        BeanUtils.copyProperties(vO, bean);
        bean = privacyEmailHistoryRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        privacyEmailHistoryRepository.deleteById(id);
    }

    public void update(Integer id, PrivacyEmailHistoryUpdateVO vO) {
        PrivacyEmailHistory bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        privacyEmailHistoryRepository.save(bean);
    }

    public PrivacyEmailHistoryDTO getById(Integer id) {
        PrivacyEmailHistory original = requireOne(id);
        return toDTO(original);
    }

    public Page<PrivacyEmailHistoryDTO> query(PrivacyEmailHistoryQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private PrivacyEmailHistoryDTO toDTO(PrivacyEmailHistory original) {
        PrivacyEmailHistoryDTO bean = new PrivacyEmailHistoryDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private PrivacyEmailHistory requireOne(Integer id) {
        return privacyEmailHistoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

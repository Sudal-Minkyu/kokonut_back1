package com.app.kokonut.refactor.emailHistory.service;

import com.app.kokonut.refactor.emailHistory.dto.EmailHistoryDTO;
import com.app.kokonut.refactor.emailHistory.entity.EmailHistory;
import com.app.kokonut.refactor.emailHistory.repository.EmailHistoryRepository;
import com.app.kokonut.refactor.emailHistory.vo.EmailHistoryQueryVO;
import com.app.kokonut.refactor.emailHistory.vo.EmailHistoryUpdateVO;
import com.app.kokonut.refactor.emailHistory.vo.EmailHistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public Integer save(EmailHistoryVO vO) {
        EmailHistory bean = new EmailHistory();
        BeanUtils.copyProperties(vO, bean);
        bean = emailHistoryRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        emailHistoryRepository.deleteById(id);
    }

    public void update(Integer id, EmailHistoryUpdateVO vO) {
        EmailHistory bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        emailHistoryRepository.save(bean);
    }

    public EmailHistoryDTO getById(Integer id) {
        EmailHistory original = requireOne(id);
        return toDTO(original);
    }

    public Page<EmailHistoryDTO> query(EmailHistoryQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private EmailHistoryDTO toDTO(EmailHistory original) {
        EmailHistoryDTO bean = new EmailHistoryDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private EmailHistory requireOne(Integer id) {
        return emailHistoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

package com.app.kokonut.refactor.emailGroup.service;

import com.app.kokonut.refactor.emailGroup.dto.EmailGroupDTO;
import com.app.kokonut.refactor.emailGroup.entity.EmailGroup;
import com.app.kokonut.refactor.emailGroup.repository.EmailGroupRepository;
import com.app.kokonut.refactor.emailGroup.vo.EmailGroupQueryVO;
import com.app.kokonut.refactor.emailGroup.vo.EmailGroupUpdateVO;
import com.app.kokonut.refactor.emailGroup.vo.EmailGroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class EmailGroupService {

    @Autowired
    private EmailGroupRepository emailGroupRepository;

    public Integer save(EmailGroupVO vO) {
        EmailGroup bean = new EmailGroup();
        BeanUtils.copyProperties(vO, bean);
        bean = emailGroupRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        emailGroupRepository.deleteById(id);
    }

    public void update(Integer id, EmailGroupUpdateVO vO) {
        EmailGroup bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        emailGroupRepository.save(bean);
    }

    public EmailGroupDTO getById(Integer id) {
        EmailGroup original = requireOne(id);
        return toDTO(original);
    }

    public Page<EmailGroupDTO> query(EmailGroupQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private EmailGroupDTO toDTO(EmailGroup original) {
        EmailGroupDTO bean = new EmailGroupDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private EmailGroup requireOne(Integer id) {
        return emailGroupRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

package com.app.kokonut.email.service;

import com.app.kokonut.email.dto.EmailDTO;
import com.app.kokonut.email.entity.Email;
import com.app.kokonut.email.repository.EmailRepository;
import com.app.kokonut.email.vo.EmailQueryVO;
import com.app.kokonut.email.vo.EmailUpdateVO;
import com.app.kokonut.email.vo.EmailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    public Integer save(EmailVO vO) {
        Email bean = new Email();
        BeanUtils.copyProperties(vO, bean);
        bean = emailRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        emailRepository.deleteById(id);
    }

    public void update(Integer id, EmailUpdateVO vO) {
        Email bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        emailRepository.save(bean);
    }

    public EmailDTO getById(Integer id) {
        Email original = requireOne(id);
        return toDTO(original);
    }

    public Page<EmailDTO> query(EmailQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private EmailDTO toDTO(Email original) {
        EmailDTO bean = new EmailDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Email requireOne(Integer id) {
        return emailRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

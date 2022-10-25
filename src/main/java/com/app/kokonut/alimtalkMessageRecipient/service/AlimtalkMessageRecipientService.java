package com.app.kokonut.alimtalkMessageRecipient.service;

import com.app.kokonut.alimtalkMessageRecipient.dto.AlimtalkMessageRecipientDTO;
import com.app.kokonut.alimtalkMessageRecipient.entity.AlimtalkMessageRecipient;
import com.app.kokonut.alimtalkMessageRecipient.repository.AlimtalkMessageRecipientRepository;
import com.app.kokonut.alimtalkMessageRecipient.vo.AlimtalkMessageRecipientQueryVO;
import com.app.kokonut.alimtalkMessageRecipient.vo.AlimtalkMessageRecipientUpdateVO;
import com.app.kokonut.alimtalkMessageRecipient.vo.AlimtalkMessageRecipientVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AlimtalkMessageRecipientService {

    @Autowired
    private AlimtalkMessageRecipientRepository alimtalkMessageRecipientRepository;

    public Integer save(AlimtalkMessageRecipientVO vO) {
        AlimtalkMessageRecipient bean = new AlimtalkMessageRecipient();
        BeanUtils.copyProperties(vO, bean);
        bean = alimtalkMessageRecipientRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        alimtalkMessageRecipientRepository.deleteById(id);
    }

    public void update(Integer id, AlimtalkMessageRecipientUpdateVO vO) {
        AlimtalkMessageRecipient bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        alimtalkMessageRecipientRepository.save(bean);
    }

    public AlimtalkMessageRecipientDTO getById(Integer id) {
        AlimtalkMessageRecipient original = requireOne(id);
        return toDTO(original);
    }

    public Page<AlimtalkMessageRecipientDTO> query(AlimtalkMessageRecipientQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AlimtalkMessageRecipientDTO toDTO(AlimtalkMessageRecipient original) {
        AlimtalkMessageRecipientDTO bean = new AlimtalkMessageRecipientDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AlimtalkMessageRecipient requireOne(Integer id) {
        return alimtalkMessageRecipientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

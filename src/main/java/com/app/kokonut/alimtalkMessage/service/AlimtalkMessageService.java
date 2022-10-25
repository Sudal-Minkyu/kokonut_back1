package com.app.kokonut.alimtalkMessage.service;

import com.app.kokonut.alimtalkMessage.dto.AlimtalkMessageDTO;
import com.app.kokonut.alimtalkMessage.entity.AlimtalkMessage;
import com.app.kokonut.alimtalkMessage.repository.AlimtalkMessageRepository;
import com.app.kokonut.alimtalkMessage.vo.AlimtalkMessageQueryVO;
import com.app.kokonut.alimtalkMessage.vo.AlimtalkMessageUpdateVO;
import com.app.kokonut.alimtalkMessage.vo.AlimtalkMessageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AlimtalkMessageService {

    @Autowired
    private AlimtalkMessageRepository alimtalkMessageRepository;

    public Integer save(AlimtalkMessageVO vO) {
        AlimtalkMessage bean = new AlimtalkMessage();
        BeanUtils.copyProperties(vO, bean);
        bean = alimtalkMessageRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        alimtalkMessageRepository.deleteById(id);
    }

    public void update(Integer id, AlimtalkMessageUpdateVO vO) {
        AlimtalkMessage bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        alimtalkMessageRepository.save(bean);
    }

    public AlimtalkMessageDTO getById(Integer id) {
        AlimtalkMessage original = requireOne(id);
        return toDTO(original);
    }

    public Page<AlimtalkMessageDTO> query(AlimtalkMessageQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AlimtalkMessageDTO toDTO(AlimtalkMessage original) {
        AlimtalkMessageDTO bean = new AlimtalkMessageDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AlimtalkMessage requireOne(Integer id) {
        return alimtalkMessageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

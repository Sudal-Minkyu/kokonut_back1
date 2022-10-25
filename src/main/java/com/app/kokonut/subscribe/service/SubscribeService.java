package com.app.kokonut.subscribe.service;

import com.app.kokonut.subscribe.dto.SubscribeDTO;
import com.app.kokonut.subscribe.entity.Subscribe;
import com.app.kokonut.subscribe.repository.SubscribeRepository;
import com.app.kokonut.subscribe.vo.SubscribeQueryVO;
import com.app.kokonut.subscribe.vo.SubscribeUpdateVO;
import com.app.kokonut.subscribe.vo.SubscribeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SubscribeService {

    @Autowired
    private SubscribeRepository subscribeRepository;

    public Integer save(SubscribeVO vO) {
        Subscribe bean = new Subscribe();
        BeanUtils.copyProperties(vO, bean);
        bean = subscribeRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        subscribeRepository.deleteById(id);
    }

    public void update(Integer id, SubscribeUpdateVO vO) {
        Subscribe bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        subscribeRepository.save(bean);
    }

    public SubscribeDTO getById(Integer id) {
        Subscribe original = requireOne(id);
        return toDTO(original);
    }

    public Page<SubscribeDTO> query(SubscribeQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private SubscribeDTO toDTO(Subscribe original) {
        SubscribeDTO bean = new SubscribeDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Subscribe requireOne(Integer id) {
        return subscribeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

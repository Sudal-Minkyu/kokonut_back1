package com.app.kokonut.refactor.shedlock.service;

import com.app.kokonut.refactor.shedlock.dto.ShedlockDTO;
import com.app.kokonut.refactor.shedlock.vo.ShedlockVO;
import com.app.kokonut.refactor.shedlock.entity.Shedlock;
import com.app.kokonut.refactor.shedlock.repository.ShedlockRepository;
import com.app.kokonut.refactor.shedlock.vo.ShedlockQueryVO;
import com.app.kokonut.refactor.shedlock.vo.ShedlockUpdateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ShedlockService {

    @Autowired
    private ShedlockRepository shedlockRepository;

    public String save(ShedlockVO vO) {
        Shedlock bean = new Shedlock();
        BeanUtils.copyProperties(vO, bean);
        bean = shedlockRepository.save(bean);
        return bean.getName();
    }

    public void delete(String id) {
        shedlockRepository.deleteById(id);
    }

    public void update(String id, ShedlockUpdateVO vO) {
        Shedlock bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        shedlockRepository.save(bean);
    }

    public ShedlockDTO getById(String id) {
        Shedlock original = requireOne(id);
        return toDTO(original);
    }

    public Page<ShedlockDTO> query(ShedlockQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ShedlockDTO toDTO(Shedlock original) {
        ShedlockDTO bean = new ShedlockDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Shedlock requireOne(String id) {
        return shedlockRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

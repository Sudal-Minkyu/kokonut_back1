package com.app.kokonut.refactor.log4jHistory.service;

import com.app.kokonut.refactor.log4jHistory.dto.Log4jHistoryDTO;
import com.app.kokonut.refactor.log4jHistory.entity.Log4jHistory;
import com.app.kokonut.refactor.log4jHistory.repository.Log4jHistoryRepository;
import com.app.kokonut.refactor.log4jHistory.vo.Log4jHistoryQueryVO;
import com.app.kokonut.refactor.log4jHistory.vo.Log4jHistoryUpdateVO;
import com.app.kokonut.refactor.log4jHistory.vo.Log4jHistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class Log4jHistoryService {

    @Autowired
    private Log4jHistoryRepository log4jHistoryRepository;

    public Integer save(Log4jHistoryVO vO) {
        Log4jHistory bean = new Log4jHistory();
        BeanUtils.copyProperties(vO, bean);
        bean = log4jHistoryRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        log4jHistoryRepository.deleteById(id);
    }

    public void update(Integer id, Log4jHistoryUpdateVO vO) {
        Log4jHistory bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        log4jHistoryRepository.save(bean);
    }

    public Log4jHistoryDTO getById(Integer id) {
        Log4jHistory original = requireOne(id);
        return toDTO(original);
    }

    public Page<Log4jHistoryDTO> query(Log4jHistoryQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private Log4jHistoryDTO toDTO(Log4jHistory original) {
        Log4jHistoryDTO bean = new Log4jHistoryDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Log4jHistory requireOne(Integer id) {
        return log4jHistoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

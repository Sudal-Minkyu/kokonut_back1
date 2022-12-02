package com.app.kokonut.refactor.statisticsDaySystem.service;

import com.app.kokonut.refactor.statisticsDaySystem.dto.StatisticsDaySystemDTO;
import com.app.kokonut.refactor.statisticsDaySystem.repository.StatisticsDaySystemRepository;
import com.app.kokonut.refactor.statisticsDaySystem.vo.StatisticsDaySystemQueryVO;
import com.app.kokonut.refactor.statisticsDaySystem.vo.StatisticsDaySystemUpdateVO;
import com.app.kokonut.refactor.statisticsDaySystem.vo.StatisticsDaySystemVO;
import com.app.kokonut.refactor.statisticsDaySystem.entity.StatisticsDaySystem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StatisticsDaySystemService {

    @Autowired
    private StatisticsDaySystemRepository statisticsDaySystemRepository;

    public Integer save(StatisticsDaySystemVO vO) {
        StatisticsDaySystem bean = new StatisticsDaySystem();
        BeanUtils.copyProperties(vO, bean);
        bean = statisticsDaySystemRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        statisticsDaySystemRepository.deleteById(id);
    }

    public void update(Integer id, StatisticsDaySystemUpdateVO vO) {
        StatisticsDaySystem bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        statisticsDaySystemRepository.save(bean);
    }

    public StatisticsDaySystemDTO getById(Integer id) {
        StatisticsDaySystem original = requireOne(id);
        return toDTO(original);
    }

    public Page<StatisticsDaySystemDTO> query(StatisticsDaySystemQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private StatisticsDaySystemDTO toDTO(StatisticsDaySystem original) {
        StatisticsDaySystemDTO bean = new StatisticsDaySystemDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private StatisticsDaySystem requireOne(Integer id) {
        return statisticsDaySystemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

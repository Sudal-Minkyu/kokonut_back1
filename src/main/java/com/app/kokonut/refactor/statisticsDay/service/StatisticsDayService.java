package com.app.kokonut.refactor.statisticsDay.service;

import com.app.kokonut.refactor.statisticsDay.dto.StatisticsDayDTO;
import com.app.kokonut.refactor.statisticsDay.vo.StatisticsDayQueryVO;
import com.app.kokonut.refactor.statisticsDay.vo.StatisticsDayUpdateVO;
import com.app.kokonut.refactor.statisticsDay.vo.StatisticsDayVO;
import com.app.kokonut.refactor.statisticsDay.entity.StatisticsDay;
import com.app.kokonut.refactor.statisticsDay.repository.StatisticsDayRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StatisticsDayService {

    @Autowired
    private StatisticsDayRepository statisticsDayRepository;

    public Integer save(StatisticsDayVO vO) {
        StatisticsDay bean = new StatisticsDay();
        BeanUtils.copyProperties(vO, bean);
        bean = statisticsDayRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        statisticsDayRepository.deleteById(id);
    }

    public void update(Integer id, StatisticsDayUpdateVO vO) {
        StatisticsDay bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        statisticsDayRepository.save(bean);
    }

    public StatisticsDayDTO getById(Integer id) {
        StatisticsDay original = requireOne(id);
        return toDTO(original);
    }

    public Page<StatisticsDayDTO> query(StatisticsDayQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private StatisticsDayDTO toDTO(StatisticsDay original) {
        StatisticsDayDTO bean = new StatisticsDayDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private StatisticsDay requireOne(Integer id) {
        return statisticsDayRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

package com.app.kokonut.statisticsDay.service;

import com.app.kokonut.statisticsDay.dto.StatisticsDayDTO;
import com.app.kokonut.statisticsDay.entity.StatisticsDay;
import com.app.kokonut.statisticsDay.repository.StatisticsDayRepository;
import com.app.kokonut.statisticsDay.vo.StatisticsDayQueryVO;
import com.app.kokonut.statisticsDay.vo.StatisticsDayUpdateVO;
import com.app.kokonut.statisticsDay.vo.StatisticsDayVO;
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

package com.app.kokonut.refactor.statisticsDay;

import com.app.kokonut.refactor.statisticsDay.dtos.StatisticsDayDto;
import com.app.kokonut.refactor.statisticsDay.vo.StatisticsDayQueryVO;
import com.app.kokonut.refactor.statisticsDay.vo.StatisticsDayUpdateVO;
import com.app.kokonut.refactor.statisticsDay.vo.StatisticsDayVO;
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

    public StatisticsDayDto getById(Integer id) {
        StatisticsDay original = requireOne(id);
        return toDTO(original);
    }

    public Page<StatisticsDayDto> query(StatisticsDayQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private StatisticsDayDto toDTO(StatisticsDay original) {
        StatisticsDayDto bean = new StatisticsDayDto();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private StatisticsDay requireOne(Integer id) {
        return statisticsDayRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

package com.app.kokonut.activityHistory.service;

import com.app.kokonut.activityHistory.dto.ActivityHistoryDTO;
import com.app.kokonut.activityHistory.entity.ActivityHistory;
import com.app.kokonut.activityHistory.repository.ActivityHistoryRepository;
import com.app.kokonut.activityHistory.vo.ActivityHistoryQueryVO;
import com.app.kokonut.activityHistory.vo.ActivityHistoryUpdateVO;
import com.app.kokonut.activityHistory.vo.ActivityHistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ActivityHistoryService {

    @Autowired
    private ActivityHistoryRepository activityHistoryRepository;

    public Integer save(ActivityHistoryVO vO) {
        ActivityHistory bean = new ActivityHistory();
        BeanUtils.copyProperties(vO, bean);
        bean = activityHistoryRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        activityHistoryRepository.deleteById(id);
    }

    public void update(Integer id, ActivityHistoryUpdateVO vO) {
        ActivityHistory bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        activityHistoryRepository.save(bean);
    }

    public ActivityHistoryDTO getById(Integer id) {
        ActivityHistory original = requireOne(id);
        return toDTO(original);
    }

    public Page<ActivityHistoryDTO> query(ActivityHistoryQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ActivityHistoryDTO toDTO(ActivityHistory original) {
        ActivityHistoryDTO bean = new ActivityHistoryDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private ActivityHistory requireOne(Integer id) {
        return activityHistoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

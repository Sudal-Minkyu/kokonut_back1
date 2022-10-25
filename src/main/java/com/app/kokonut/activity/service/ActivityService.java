package com.app.kokonut.activity.service;

import com.app.kokonut.activity.entity.Activity;
import com.app.kokonut.activity.vo.ActivityQueryVO;
import com.app.kokonut.activity.vo.ActivityUpdateVO;
import com.app.kokonut.activity.vo.ActivityVO;
import com.app.kokonut.activity.dto.ActivityDTO;
import com.app.kokonut.activity.repository.ActivityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Integer save(ActivityVO vO) {
        Activity bean = new Activity();
        BeanUtils.copyProperties(vO, bean);
        bean = activityRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        activityRepository.deleteById(id);
    }

    public void update(Integer id, ActivityUpdateVO vO) {
        Activity bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        activityRepository.save(bean);
    }

    public ActivityDTO getById(Integer id) {
        Activity original = requireOne(id);
        return toDTO(original);
    }

    public Page<ActivityDTO> query(ActivityQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ActivityDTO toDTO(Activity original) {
        ActivityDTO bean = new ActivityDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Activity requireOne(Integer id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

package com.app.kokonut.refactor.setting.service;

import com.app.kokonut.refactor.setting.dto.SettingDTO;
import com.app.kokonut.refactor.setting.repository.SettingRepository;
import com.app.kokonut.refactor.setting.vo.SettingQueryVO;
import com.app.kokonut.refactor.setting.vo.SettingUpdateVO;
import com.app.kokonut.refactor.setting.vo.SettingVO;
import com.app.kokonut.refactor.setting.entity.Setting;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    public Integer save(SettingVO vO) {
        Setting bean = new Setting();
        BeanUtils.copyProperties(vO, bean);
        bean = settingRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        settingRepository.deleteById(id);
    }

    public void update(Integer id, SettingUpdateVO vO) {
        Setting bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        settingRepository.save(bean);
    }

    public SettingDTO getById(Integer id) {
        Setting original = requireOne(id);
        return toDTO(original);
    }

    public Page<SettingDTO> query(SettingQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private SettingDTO toDTO(Setting original) {
        SettingDTO bean = new SettingDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Setting requireOne(Integer id) {
        return settingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

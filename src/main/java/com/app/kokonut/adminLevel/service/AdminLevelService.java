package com.app.kokonut.adminLevel.service;

import com.app.kokonut.adminLevel.dto.AdminLevelDTO;
import com.app.kokonut.adminLevel.entity.AdminLevel;
import com.app.kokonut.adminLevel.repository.AdminLevelRepository;
import com.app.kokonut.adminLevel.vo.AdminLevelQueryVO;
import com.app.kokonut.adminLevel.vo.AdminLevelUpdateVO;
import com.app.kokonut.adminLevel.vo.AdminLevelVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AdminLevelService {

    @Autowired
    private AdminLevelRepository adminLevelRepository;

    public Integer save(AdminLevelVO vO) {
        AdminLevel bean = new AdminLevel();
        BeanUtils.copyProperties(vO, bean);
        bean = adminLevelRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        adminLevelRepository.deleteById(id);
    }

    public void update(Integer id, AdminLevelUpdateVO vO) {
        AdminLevel bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        adminLevelRepository.save(bean);
    }

    public AdminLevelDTO getById(Integer id) {
        AdminLevel original = requireOne(id);
        return toDTO(original);
    }

    public Page<AdminLevelDTO> query(AdminLevelQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AdminLevelDTO toDTO(AdminLevel original) {
        AdminLevelDTO bean = new AdminLevelDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AdminLevel requireOne(Integer id) {
        return adminLevelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

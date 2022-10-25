package com.app.kokonut.adminRemove.service;

import com.app.kokonut.adminRemove.dto.AdminRemoveDTO;
import com.app.kokonut.adminRemove.entity.AdminRemove;
import com.app.kokonut.adminRemove.repository.AdminRemoveRepository;
import com.app.kokonut.adminRemove.vo.AdminRemoveQueryVO;
import com.app.kokonut.adminRemove.vo.AdminRemoveUpdateVO;
import com.app.kokonut.adminRemove.vo.AdminRemoveVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AdminRemoveService {

    @Autowired
    private AdminRemoveRepository adminRemoveRepository;

    public Integer save(AdminRemoveVO vO) {
        AdminRemove bean = new AdminRemove();
        BeanUtils.copyProperties(vO, bean);
        bean = adminRemoveRepository.save(bean);
        return bean.getAdminIdx();
    }

    public void delete(Integer id) {
        adminRemoveRepository.deleteById(id);
    }

    public void update(Integer id, AdminRemoveUpdateVO vO) {
        AdminRemove bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        adminRemoveRepository.save(bean);
    }

    public AdminRemoveDTO getById(Integer id) {
        AdminRemove original = requireOne(id);
        return toDTO(original);
    }

    public Page<AdminRemoveDTO> query(AdminRemoveQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AdminRemoveDTO toDTO(AdminRemove original) {
        AdminRemoveDTO bean = new AdminRemoveDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AdminRemove requireOne(Integer id) {
        return adminRemoveRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

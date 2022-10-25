package com.app.kokonut.admin.service;

import com.app.kokonut.admin.dto.AdminDTO;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.admin.repository.AdminRepository;
import com.app.kokonut.admin.vo.AdminQueryVO;
import com.app.kokonut.admin.vo.AdminUpdateVO;
import com.app.kokonut.admin.vo.AdminVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Integer save(AdminVO vO) {
        Admin bean = new Admin();
        BeanUtils.copyProperties(vO, bean);
        bean = adminRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        adminRepository.deleteById(id);
    }

    public void update(Integer id, AdminUpdateVO vO) {
        Admin bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        adminRepository.save(bean);
    }

    public AdminDTO getById(Integer id) {
        Admin original = requireOne(id);
        return toDTO(original);
    }

    public Page<AdminDTO> query(AdminQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AdminDTO toDTO(Admin original) {
        AdminDTO bean = new AdminDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Admin requireOne(Integer id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

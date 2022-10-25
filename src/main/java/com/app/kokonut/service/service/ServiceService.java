package com.app.kokonut.service.service;

import com.app.kokonut.service.dto.ServiceDTO;
import com.app.kokonut.service.entity.Service;
import com.app.kokonut.service.repository.ServiceRepository;
import com.app.kokonut.service.vo.ServiceQueryVO;
import com.app.kokonut.service.vo.ServiceUpdateVO;
import com.app.kokonut.service.vo.ServiceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public Integer save(ServiceVO vO) {
        Service bean = new Service();
        BeanUtils.copyProperties(vO, bean);
        bean = serviceRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        serviceRepository.deleteById(id);
    }

    public void update(Integer id, ServiceUpdateVO vO) {
        Service bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        serviceRepository.save(bean);
    }

    public ServiceDTO getById(Integer id) {
        Service original = requireOne(id);
        return toDTO(original);
    }

    public Page<ServiceDTO> query(ServiceQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ServiceDTO toDTO(Service original) {
        ServiceDTO bean = new ServiceDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Service requireOne(Integer id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

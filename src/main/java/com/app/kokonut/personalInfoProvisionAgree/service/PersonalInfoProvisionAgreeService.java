package com.app.kokonut.personalInfoProvisionAgree.service;

import com.app.kokonut.personalInfoProvisionAgree.dto.PersonalInfoProvisionAgreeDTO;
import com.app.kokonut.personalInfoProvisionAgree.entity.PersonalInfoProvisionAgree;
import com.app.kokonut.personalInfoProvisionAgree.repository.PersonalInfoProvisionAgreeRepository;
import com.app.kokonut.personalInfoProvisionAgree.vo.PersonalInfoProvisionAgreeQueryVO;
import com.app.kokonut.personalInfoProvisionAgree.vo.PersonalInfoProvisionAgreeUpdateVO;
import com.app.kokonut.personalInfoProvisionAgree.vo.PersonalInfoProvisionAgreeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PersonalInfoProvisionAgreeService {

    @Autowired
    private PersonalInfoProvisionAgreeRepository personalInfoProvisionAgreeRepository;

    public Integer save(PersonalInfoProvisionAgreeVO vO) {
        PersonalInfoProvisionAgree bean = new PersonalInfoProvisionAgree();
        BeanUtils.copyProperties(vO, bean);
        bean = personalInfoProvisionAgreeRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        personalInfoProvisionAgreeRepository.deleteById(id);
    }

    public void update(Integer id, PersonalInfoProvisionAgreeUpdateVO vO) {
        PersonalInfoProvisionAgree bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        personalInfoProvisionAgreeRepository.save(bean);
    }

    public PersonalInfoProvisionAgreeDTO getById(Integer id) {
        PersonalInfoProvisionAgree original = requireOne(id);
        return toDTO(original);
    }

    public Page<PersonalInfoProvisionAgreeDTO> query(PersonalInfoProvisionAgreeQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private PersonalInfoProvisionAgreeDTO toDTO(PersonalInfoProvisionAgree original) {
        PersonalInfoProvisionAgreeDTO bean = new PersonalInfoProvisionAgreeDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private PersonalInfoProvisionAgree requireOne(Integer id) {
        return personalInfoProvisionAgreeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

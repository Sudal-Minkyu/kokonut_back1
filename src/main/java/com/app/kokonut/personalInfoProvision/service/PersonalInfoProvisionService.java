package com.app.kokonut.personalInfoProvision.service;

import com.app.kokonut.personalInfoProvision.dto.PersonalInfoProvisionDTO;
import com.app.kokonut.personalInfoProvision.entity.PersonalInfoProvision;
import com.app.kokonut.personalInfoProvision.repository.PersonalInfoProvisionRepository;
import com.app.kokonut.personalInfoProvision.vo.PersonalInfoProvisionQueryVO;
import com.app.kokonut.personalInfoProvision.vo.PersonalInfoProvisionUpdateVO;
import com.app.kokonut.personalInfoProvision.vo.PersonalInfoProvisionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PersonalInfoProvisionService {

    @Autowired
    private PersonalInfoProvisionRepository personalInfoProvisionRepository;

    public Integer save(PersonalInfoProvisionVO vO) {
        PersonalInfoProvision bean = new PersonalInfoProvision();
        BeanUtils.copyProperties(vO, bean);
        bean = personalInfoProvisionRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        personalInfoProvisionRepository.deleteById(id);
    }

    public void update(Integer id, PersonalInfoProvisionUpdateVO vO) {
        PersonalInfoProvision bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        personalInfoProvisionRepository.save(bean);
    }

    public PersonalInfoProvisionDTO getById(Integer id) {
        PersonalInfoProvision original = requireOne(id);
        return toDTO(original);
    }

    public Page<PersonalInfoProvisionDTO> query(PersonalInfoProvisionQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private PersonalInfoProvisionDTO toDTO(PersonalInfoProvision original) {
        PersonalInfoProvisionDTO bean = new PersonalInfoProvisionDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private PersonalInfoProvision requireOne(Integer id) {
        return personalInfoProvisionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

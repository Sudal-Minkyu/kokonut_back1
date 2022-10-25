package com.app.kokonut.personalInfoProvisionHistory.service;

import com.app.kokonut.personalInfoProvisionHistory.dto.PersonalInfoProvisionHistoryDTO;
import com.app.kokonut.personalInfoProvisionHistory.entity.PersonalInfoProvisionHistory;
import com.app.kokonut.personalInfoProvisionHistory.repository.PersonalInfoProvisionHistoryRepository;
import com.app.kokonut.personalInfoProvisionHistory.vo.PersonalInfoProvisionHistoryQueryVO;
import com.app.kokonut.personalInfoProvisionHistory.vo.PersonalInfoProvisionHistoryUpdateVO;
import com.app.kokonut.personalInfoProvisionHistory.vo.PersonalInfoProvisionHistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PersonalInfoProvisionHistoryService {

    @Autowired
    private PersonalInfoProvisionHistoryRepository personalInfoProvisionHistoryRepository;

    public Integer save(PersonalInfoProvisionHistoryVO vO) {
        PersonalInfoProvisionHistory bean = new PersonalInfoProvisionHistory();
        BeanUtils.copyProperties(vO, bean);
        bean = personalInfoProvisionHistoryRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        personalInfoProvisionHistoryRepository.deleteById(id);
    }

    public void update(Integer id, PersonalInfoProvisionHistoryUpdateVO vO) {
        PersonalInfoProvisionHistory bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        personalInfoProvisionHistoryRepository.save(bean);
    }

    public PersonalInfoProvisionHistoryDTO getById(Integer id) {
        PersonalInfoProvisionHistory original = requireOne(id);
        return toDTO(original);
    }

    public Page<PersonalInfoProvisionHistoryDTO> query(PersonalInfoProvisionHistoryQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private PersonalInfoProvisionHistoryDTO toDTO(PersonalInfoProvisionHistory original) {
        PersonalInfoProvisionHistoryDTO bean = new PersonalInfoProvisionHistoryDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private PersonalInfoProvisionHistory requireOne(Integer id) {
        return personalInfoProvisionHistoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

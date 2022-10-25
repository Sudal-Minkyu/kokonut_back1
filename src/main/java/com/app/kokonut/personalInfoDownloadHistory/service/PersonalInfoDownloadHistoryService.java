package com.app.kokonut.personalInfoDownloadHistory.service;

import com.app.kokonut.personalInfoDownloadHistory.dto.PersonalInfoDownloadHistoryDTO;
import com.app.kokonut.personalInfoDownloadHistory.entity.PersonalInfoDownloadHistory;
import com.app.kokonut.personalInfoDownloadHistory.repository.PersonalInfoDownloadHistoryRepository;
import com.app.kokonut.personalInfoDownloadHistory.vo.PersonalInfoDownloadHistoryQueryVO;
import com.app.kokonut.personalInfoDownloadHistory.vo.PersonalInfoDownloadHistoryUpdateVO;
import com.app.kokonut.personalInfoDownloadHistory.vo.PersonalInfoDownloadHistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PersonalInfoDownloadHistoryService {

    @Autowired
    private PersonalInfoDownloadHistoryRepository personalInfoDownloadHistoryRepository;

    public Integer save(PersonalInfoDownloadHistoryVO vO) {
        PersonalInfoDownloadHistory bean = new PersonalInfoDownloadHistory();
        BeanUtils.copyProperties(vO, bean);
        bean = personalInfoDownloadHistoryRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        personalInfoDownloadHistoryRepository.deleteById(id);
    }

    public void update(Integer id, PersonalInfoDownloadHistoryUpdateVO vO) {
        PersonalInfoDownloadHistory bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        personalInfoDownloadHistoryRepository.save(bean);
    }

    public PersonalInfoDownloadHistoryDTO getById(Integer id) {
        PersonalInfoDownloadHistory original = requireOne(id);
        return toDTO(original);
    }

    public Page<PersonalInfoDownloadHistoryDTO> query(PersonalInfoDownloadHistoryQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private PersonalInfoDownloadHistoryDTO toDTO(PersonalInfoDownloadHistory original) {
        PersonalInfoDownloadHistoryDTO bean = new PersonalInfoDownloadHistoryDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private PersonalInfoDownloadHistory requireOne(Integer id) {
        return personalInfoDownloadHistoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

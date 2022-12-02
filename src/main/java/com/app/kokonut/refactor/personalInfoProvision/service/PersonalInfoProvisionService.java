package com.app.kokonut.refactor.personalInfoProvision.service;

import com.app.kokonut.refactor.personalInfoProvision.dto.PersonalInfoProvisionListDto;
import com.app.kokonut.refactor.personalInfoProvision.dto.PersonalInfoProvisionMapperDto;
import com.app.kokonut.refactor.personalInfoProvision.repository.PersonalInfoProvisionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Woody
 * Date : 2022-11-02
 * Remark :
 */
@Slf4j
@Service
public class PersonalInfoProvisionService {

    private final PersonalInfoProvisionRepository personalInfoProvisionRepository;

    @Autowired
    public PersonalInfoProvisionService(PersonalInfoProvisionRepository personalInfoProvisionRepository){
        this.personalInfoProvisionRepository = personalInfoProvisionRepository;
    }

    /**
     * 정보제공 목록 조회
     */
    public List<PersonalInfoProvisionListDto> findByProvisionList(PersonalInfoProvisionMapperDto personalInfoProvisionMapperDto) {
        log.info("findByProvisionList 호출");
        return personalInfoProvisionRepository.findByProvisionList(personalInfoProvisionMapperDto);
    }


    //    public Integer save(PersonalInfoProvisionVO vO) {
//        PersonalInfoProvision bean = new PersonalInfoProvision();
//        BeanUtils.copyProperties(vO, bean);
//        bean = personalInfoProvisionRepository.save(bean);
//        return bean.getIdx();
//    }
//
//    public void delete(Integer id) {
//        personalInfoProvisionRepository.deleteById(id);
//    }
//
//    public void update(Integer id, PersonalInfoProvisionUpdateVO vO) {
//        PersonalInfoProvision bean = requireOne(id);
//        BeanUtils.copyProperties(vO, bean);
//        personalInfoProvisionRepository.save(bean);
//    }
//
//    public PersonalInfoProvisionDTO getById(Integer id) {
//        PersonalInfoProvision original = requireOne(id);
//        return toDTO(original);
//    }
//
//    public Page<PersonalInfoProvisionDTO> query(PersonalInfoProvisionQueryVO vO) {
//        throw new UnsupportedOperationException();
//    }
//
//    private PersonalInfoProvisionDTO toDTO(PersonalInfoProvision original) {
//        PersonalInfoProvisionDTO bean = new PersonalInfoProvisionDTO();
//        BeanUtils.copyProperties(original, bean);
//        return bean;
//    }
//
//    private PersonalInfoProvision requireOne(Integer id) {
//        return personalInfoProvisionRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
//    }


//    public Integer save(PersonalInfoProvisionVO vO) {
//        PersonalInfoProvision bean = new PersonalInfoProvision();
//        BeanUtils.copyProperties(vO, bean);
//        bean = personalInfoProvisionRepository.save(bean);
//        return bean.getIdx();
//    }
//
//    public void delete(Integer id) {
//        personalInfoProvisionRepository.deleteById(id);
//    }
//
//    public void update(Integer id, PersonalInfoProvisionUpdateVO vO) {
//        PersonalInfoProvision bean = requireOne(id);
//        BeanUtils.copyProperties(vO, bean);
//        personalInfoProvisionRepository.save(bean);
//    }
//
//    public PersonalInfoProvisionDTO getById(Integer id) {
//        PersonalInfoProvision original = requireOne(id);
//        return toDTO(original);
//    }
//
//    public Page<PersonalInfoProvisionDTO> query(PersonalInfoProvisionQueryVO vO) {
//        throw new UnsupportedOperationException();
//    }
//
//    private PersonalInfoProvisionDTO toDTO(PersonalInfoProvision original) {
//        PersonalInfoProvisionDTO bean = new PersonalInfoProvisionDTO();
//        BeanUtils.copyProperties(original, bean);
//        return bean;
//    }
//
//    private PersonalInfoProvision requireOne(Integer id) {
//        return personalInfoProvisionRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
//    }
}

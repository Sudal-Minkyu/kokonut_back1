package com.app.kokonut.refactor.collectInformation.service;

import com.app.kokonut.refactor.collectInformation.dto.CollectInformationDTO;
import com.app.kokonut.refactor.collectInformation.entity.CollectInformation;
import com.app.kokonut.refactor.collectInformation.repository.CollectInformationRepository;
import com.app.kokonut.refactor.collectInformation.vo.CollectInformationQueryVO;
import com.app.kokonut.refactor.collectInformation.vo.CollectInformationUpdateVO;
import com.app.kokonut.refactor.collectInformation.vo.CollectInformationVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CollectInformationService {

    @Autowired
    private CollectInformationRepository collectInformationRepository;

    public Integer save(CollectInformationVO vO) {
        CollectInformation bean = new CollectInformation();
        BeanUtils.copyProperties(vO, bean);
        bean = collectInformationRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        collectInformationRepository.deleteById(id);
    }

    public void update(Integer id, CollectInformationUpdateVO vO) {
        CollectInformation bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        collectInformationRepository.save(bean);
    }

    public CollectInformationDTO getById(Integer id) {
        CollectInformation original = requireOne(id);
        return toDTO(original);
    }

    public Page<CollectInformationDTO> query(CollectInformationQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private CollectInformationDTO toDTO(CollectInformation original) {
        CollectInformationDTO bean = new CollectInformationDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private CollectInformation requireOne(Integer id) {
        return collectInformationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

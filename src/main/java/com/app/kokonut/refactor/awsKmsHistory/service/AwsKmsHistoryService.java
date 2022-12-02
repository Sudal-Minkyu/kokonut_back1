package com.app.kokonut.refactor.awsKmsHistory.service;

import com.app.kokonut.refactor.awsKmsHistory.dto.AwsKmsHistoryDTO;
import com.app.kokonut.refactor.awsKmsHistory.entity.AwsKmsHistory;
import com.app.kokonut.refactor.awsKmsHistory.repository.AwsKmsHistoryRepository;
import com.app.kokonut.refactor.awsKmsHistory.vo.AwsKmsHistoryQueryVO;
import com.app.kokonut.refactor.awsKmsHistory.vo.AwsKmsHistoryUpdateVO;
import com.app.kokonut.refactor.awsKmsHistory.vo.AwsKmsHistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AwsKmsHistoryService {

    @Autowired
    private AwsKmsHistoryRepository awsKmsHistoryRepository;

    public Integer save(AwsKmsHistoryVO vO) {
        AwsKmsHistory bean = new AwsKmsHistory();
        BeanUtils.copyProperties(vO, bean);
        bean = awsKmsHistoryRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        awsKmsHistoryRepository.deleteById(id);
    }

    public void update(Integer id, AwsKmsHistoryUpdateVO vO) {
        AwsKmsHistory bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        awsKmsHistoryRepository.save(bean);
    }

    public AwsKmsHistoryDTO getById(Integer id) {
        AwsKmsHistory original = requireOne(id);
        return toDTO(original);
    }

    public Page<AwsKmsHistoryDTO> query(AwsKmsHistoryQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AwsKmsHistoryDTO toDTO(AwsKmsHistory original) {
        AwsKmsHistoryDTO bean = new AwsKmsHistoryDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AwsKmsHistory requireOne(Integer id) {
        return awsKmsHistoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

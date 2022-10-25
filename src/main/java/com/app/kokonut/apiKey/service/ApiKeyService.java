package com.app.kokonut.apiKey.service;

import com.app.kokonut.apiKey.dto.ApiKeyDTO;
import com.app.kokonut.apiKey.entity.ApiKey;
import com.app.kokonut.apiKey.repository.ApiKeyRepository;
import com.app.kokonut.apiKey.vo.ApiKeyQueryVO;
import com.app.kokonut.apiKey.vo.ApiKeyUpdateVO;
import com.app.kokonut.apiKey.vo.ApiKeyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ApiKeyService {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    public Integer save(ApiKeyVO vO) {
        ApiKey bean = new ApiKey();
        BeanUtils.copyProperties(vO, bean);
        bean = apiKeyRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        apiKeyRepository.deleteById(id);
    }

    public void update(Integer id, ApiKeyUpdateVO vO) {
        ApiKey bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        apiKeyRepository.save(bean);
    }

    public ApiKeyDTO getById(Integer id) {
        ApiKey original = requireOne(id);
        return toDTO(original);
    }

    public Page<ApiKeyDTO> query(ApiKeyQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ApiKeyDTO toDTO(ApiKey original) {
        ApiKeyDTO bean = new ApiKeyDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private ApiKey requireOne(Integer id) {
        return apiKeyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

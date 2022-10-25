package com.app.kokonut.policy.service;

import com.app.kokonut.policy.dto.PolicyDTO;
import com.app.kokonut.policy.entity.Policy;
import com.app.kokonut.policy.repository.PolicyRepository;
import com.app.kokonut.policy.vo.PolicyQueryVO;
import com.app.kokonut.policy.vo.PolicyUpdateVO;
import com.app.kokonut.policy.vo.PolicyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public Integer save(PolicyVO vO) {
        Policy bean = new Policy();
        BeanUtils.copyProperties(vO, bean);
        bean = policyRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        policyRepository.deleteById(id);
    }

    public void update(Integer id, PolicyUpdateVO vO) {
        Policy bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        policyRepository.save(bean);
    }

    public PolicyDTO getById(Integer id) {
        Policy original = requireOne(id);
        return toDTO(original);
    }

    public Page<PolicyDTO> query(PolicyQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private PolicyDTO toDTO(Policy original) {
        PolicyDTO bean = new PolicyDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Policy requireOne(Integer id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

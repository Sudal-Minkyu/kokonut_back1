package com.app.kokonut.company.service;

import com.app.kokonut.company.dto.CompanyDTO;
import com.app.kokonut.company.entity.Company;
import com.app.kokonut.company.repository.CompanyRepository;
import com.app.kokonut.company.vo.CompanyQueryVO;
import com.app.kokonut.company.vo.CompanyUpdateVO;
import com.app.kokonut.company.vo.CompanyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Integer save(CompanyVO vO) {
        Company bean = new Company();
        BeanUtils.copyProperties(vO, bean);
        bean = companyRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        companyRepository.deleteById(id);
    }

    public void update(Integer id, CompanyUpdateVO vO) {
        Company bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        companyRepository.save(bean);
    }

    public CompanyDTO getById(Integer id) {
        Company original = requireOne(id);
        return toDTO(original);
    }

    public Page<CompanyDTO> query(CompanyQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private CompanyDTO toDTO(Company original) {
        CompanyDTO bean = new CompanyDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Company requireOne(Integer id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

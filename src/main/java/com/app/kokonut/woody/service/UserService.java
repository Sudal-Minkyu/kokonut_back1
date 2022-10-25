package com.app.kokonut.woody.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-10-21
 * Time :
 * Remark : Kokonut User Service
 */
@Slf4j
@Service
public class UserService {

//    private final CompanyRepository companyRepository;
//
//    @Autowired
//    public UserService(CompanyRepository companyRepository){
//        this.companyRepository = companyRepository;
//    }
//
//    public ResponseEntity<Map<String, Object>> companyDetail(Integer IDX) {
//        log.info("companyDetail 호출 ");
//
//        AjaxResponse res = new AjaxResponse();
//        HashMap<String, Object> data = new HashMap<>();
//
//        CompanyListDto company = companyRepository.findByCompany(IDX);
//        log.info("company : "+company);
//        data.put("company",company);
//
//        return ResponseEntity.ok(res.success(data));
//    }

}

package com.app.kokonut.woody.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark : Kokonut Member Service
 */
@Slf4j
@Service
public class MemberService {

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

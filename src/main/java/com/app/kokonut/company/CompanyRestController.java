package com.app.kokonut.company;

import com.app.kokonut.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Woody
 * Date : 2022-12-22
 * Time :
 * Remark : 기업 관련 RestController
 */
@Slf4j
@RequestMapping("/api/Company")
@RestController
public class CompanyRestController {

    private final CompanyService companyService;

    @Autowired
    public CompanyRestController(CompanyService companyService){
        this.companyService = companyService;
    }


}

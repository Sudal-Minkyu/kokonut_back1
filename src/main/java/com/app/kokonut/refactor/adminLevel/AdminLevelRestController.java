package com.app.kokonut.refactor.adminLevel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v2/api/AdminLevel")
public class AdminLevelRestController {

    private AdminLevelService adminLevelService;

    @Autowired
    public AdminLevelRestController(AdminLevelService adminLevelService){
        this.adminLevelService = adminLevelService;
    }

}

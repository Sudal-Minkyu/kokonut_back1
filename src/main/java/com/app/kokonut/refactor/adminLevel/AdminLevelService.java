package com.app.kokonut.refactor.adminLevel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminLevelService {

    private final AdminLevelRepository adminLevelRepository;

    @Autowired
    public AdminLevelService(AdminLevelRepository adminLevelRepository){
        this.adminLevelRepository = adminLevelRepository;
    }




}

package com.app.kokonut.admin.controller;

import com.app.kokonut.admin.dto.AdminDTO;
import com.app.kokonut.admin.service.AdminService;
import com.app.kokonut.admin.vo.AdminQueryVO;
import com.app.kokonut.admin.vo.AdminUpdateVO;
import com.app.kokonut.admin.vo.AdminVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(tags = "")
@Validated
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody AdminVO vO) {
        return adminService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        adminService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody AdminUpdateVO vO) {
        adminService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public AdminDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return adminService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<AdminDTO> query(@Valid AdminQueryVO vO) {
        return adminService.query(vO);
    }
}

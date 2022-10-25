package com.app.kokonut.adminLevel.controller;

import com.app.kokonut.adminLevel.dto.AdminLevelDTO;
import com.app.kokonut.adminLevel.service.AdminLevelService;
import com.app.kokonut.adminLevel.vo.AdminLevelQueryVO;
import com.app.kokonut.adminLevel.vo.AdminLevelUpdateVO;
import com.app.kokonut.adminLevel.vo.AdminLevelVO;
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
@RequestMapping("/adminLevel")
public class AdminLevelController {

    @Autowired
    private AdminLevelService adminLevelService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody AdminLevelVO vO) {
        return adminLevelService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        adminLevelService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody AdminLevelUpdateVO vO) {
        adminLevelService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public AdminLevelDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return adminLevelService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<AdminLevelDTO> query(@Valid AdminLevelQueryVO vO) {
        return adminLevelService.query(vO);
    }
}

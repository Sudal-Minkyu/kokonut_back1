package com.app.kokonut.adminRemove.controller;

import com.app.kokonut.adminRemove.dto.AdminRemoveDTO;
import com.app.kokonut.adminRemove.service.AdminRemoveService;
import com.app.kokonut.adminRemove.vo.AdminRemoveQueryVO;
import com.app.kokonut.adminRemove.vo.AdminRemoveUpdateVO;
import com.app.kokonut.adminRemove.vo.AdminRemoveVO;
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
@RequestMapping("/adminRemove")
public class AdminRemoveController {

    @Autowired
    private AdminRemoveService adminRemoveService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody AdminRemoveVO vO) {
        return adminRemoveService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        adminRemoveService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody AdminRemoveUpdateVO vO) {
        adminRemoveService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public AdminRemoveDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return adminRemoveService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<AdminRemoveDTO> query(@Valid AdminRemoveQueryVO vO) {
        return adminRemoveService.query(vO);
    }
}

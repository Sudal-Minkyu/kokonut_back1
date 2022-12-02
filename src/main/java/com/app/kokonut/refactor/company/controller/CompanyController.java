package com.app.kokonut.refactor.company.controller;

import com.app.kokonut.refactor.company.dto.CompanyDTO;
import com.app.kokonut.refactor.company.service.CompanyService;
import com.app.kokonut.refactor.company.vo.CompanyQueryVO;
import com.app.kokonut.refactor.company.vo.CompanyUpdateVO;
import com.app.kokonut.refactor.company.vo.CompanyVO;
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
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody CompanyVO vO) {
        return companyService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        companyService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody CompanyUpdateVO vO) {
        companyService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public CompanyDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return companyService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<CompanyDTO> query(@Valid CompanyQueryVO vO) {
        return companyService.query(vO);
    }
}

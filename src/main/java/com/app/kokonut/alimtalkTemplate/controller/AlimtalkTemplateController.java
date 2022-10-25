package com.app.kokonut.alimtalkTemplate.controller;

import com.app.kokonut.alimtalkTemplate.dto.AlimtalkTemplateDTO;
import com.app.kokonut.alimtalkTemplate.service.AlimtalkTemplateService;
import com.app.kokonut.alimtalkTemplate.vo.AlimtalkTemplateQueryVO;
import com.app.kokonut.alimtalkTemplate.vo.AlimtalkTemplateUpdateVO;
import com.app.kokonut.alimtalkTemplate.vo.AlimtalkTemplateVO;
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
@RequestMapping("/alimtalkTemplate")
public class AlimtalkTemplateController {

    @Autowired
    private AlimtalkTemplateService alimtalkTemplateService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody AlimtalkTemplateVO vO) {
        return alimtalkTemplateService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        alimtalkTemplateService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody AlimtalkTemplateUpdateVO vO) {
        alimtalkTemplateService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public AlimtalkTemplateDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return alimtalkTemplateService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<AlimtalkTemplateDTO> query(@Valid AlimtalkTemplateQueryVO vO) {
        return alimtalkTemplateService.query(vO);
    }
}

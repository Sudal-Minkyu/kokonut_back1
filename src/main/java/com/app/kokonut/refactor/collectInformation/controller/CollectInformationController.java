package com.app.kokonut.refactor.collectInformation.controller;

import com.app.kokonut.refactor.collectInformation.dto.CollectInformationDTO;
import com.app.kokonut.refactor.collectInformation.service.CollectInformationService;
import com.app.kokonut.refactor.collectInformation.vo.CollectInformationQueryVO;
import com.app.kokonut.refactor.collectInformation.vo.CollectInformationUpdateVO;
import com.app.kokonut.refactor.collectInformation.vo.CollectInformationVO;
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
@RequestMapping("/collectInformation")
public class CollectInformationController {

    @Autowired
    private CollectInformationService collectInformationService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody CollectInformationVO vO) {
        return collectInformationService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        collectInformationService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody CollectInformationUpdateVO vO) {
        collectInformationService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public CollectInformationDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return collectInformationService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<CollectInformationDTO> query(@Valid CollectInformationQueryVO vO) {
        return collectInformationService.query(vO);
    }
}

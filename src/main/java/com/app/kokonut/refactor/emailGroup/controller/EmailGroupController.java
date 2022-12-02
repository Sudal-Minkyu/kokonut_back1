package com.app.kokonut.refactor.emailGroup.controller;

import com.app.kokonut.refactor.emailGroup.dto.EmailGroupDTO;
import com.app.kokonut.refactor.emailGroup.service.EmailGroupService;
import com.app.kokonut.refactor.emailGroup.vo.EmailGroupQueryVO;
import com.app.kokonut.refactor.emailGroup.vo.EmailGroupUpdateVO;
import com.app.kokonut.refactor.emailGroup.vo.EmailGroupVO;
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
@RequestMapping("/emailGroup")
public class EmailGroupController {

    @Autowired
    private EmailGroupService emailGroupService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody EmailGroupVO vO) {
        return emailGroupService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        emailGroupService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody EmailGroupUpdateVO vO) {
        emailGroupService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public EmailGroupDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return emailGroupService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<EmailGroupDTO> query(@Valid EmailGroupQueryVO vO) {
        return emailGroupService.query(vO);
    }
}

package com.app.kokonut.refactor.email.controller;

import com.app.kokonut.refactor.email.dto.EmailDTO;
import com.app.kokonut.refactor.email.service.EmailService;
import com.app.kokonut.refactor.email.vo.EmailQueryVO;
import com.app.kokonut.refactor.email.vo.EmailUpdateVO;
import com.app.kokonut.refactor.email.vo.EmailVO;
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
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody EmailVO vO) {
        return emailService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        emailService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody EmailUpdateVO vO) {
        emailService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public EmailDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return emailService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<EmailDTO> query(@Valid EmailQueryVO vO) {
        return emailService.query(vO);
    }
}

package com.app.kokonut.privacyEmail.controller;

import com.app.kokonut.privacyEmail.dto.PrivacyEmailDTO;
import com.app.kokonut.privacyEmail.service.PrivacyEmailService;
import com.app.kokonut.privacyEmail.vo.PrivacyEmailQueryVO;
import com.app.kokonut.privacyEmail.vo.PrivacyEmailUpdateVO;
import com.app.kokonut.privacyEmail.vo.PrivacyEmailVO;
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
@RequestMapping("/privacyEmail")
public class PrivacyEmailController {

    @Autowired
    private PrivacyEmailService privacyEmailService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody PrivacyEmailVO vO) {
        return privacyEmailService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        privacyEmailService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody PrivacyEmailUpdateVO vO) {
        privacyEmailService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public PrivacyEmailDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return privacyEmailService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<PrivacyEmailDTO> query(@Valid PrivacyEmailQueryVO vO) {
        return privacyEmailService.query(vO);
    }
}

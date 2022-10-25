package com.app.kokonut.personalInfoProvisionAgree.controller;

import com.app.kokonut.personalInfoProvisionAgree.dto.PersonalInfoProvisionAgreeDTO;
import com.app.kokonut.personalInfoProvisionAgree.service.PersonalInfoProvisionAgreeService;
import com.app.kokonut.personalInfoProvisionAgree.vo.PersonalInfoProvisionAgreeQueryVO;
import com.app.kokonut.personalInfoProvisionAgree.vo.PersonalInfoProvisionAgreeUpdateVO;
import com.app.kokonut.personalInfoProvisionAgree.vo.PersonalInfoProvisionAgreeVO;
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
@RequestMapping("/personalInfoProvisionAgree")
public class PersonalInfoProvisionAgreeController {

    @Autowired
    private PersonalInfoProvisionAgreeService personalInfoProvisionAgreeService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody PersonalInfoProvisionAgreeVO vO) {
        return personalInfoProvisionAgreeService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        personalInfoProvisionAgreeService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody PersonalInfoProvisionAgreeUpdateVO vO) {
        personalInfoProvisionAgreeService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public PersonalInfoProvisionAgreeDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return personalInfoProvisionAgreeService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<PersonalInfoProvisionAgreeDTO> query(@Valid PersonalInfoProvisionAgreeQueryVO vO) {
        return personalInfoProvisionAgreeService.query(vO);
    }
}

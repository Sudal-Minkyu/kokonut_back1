package com.app.kokonut.personalInfoProvision.controller;

import com.app.kokonut.personalInfoProvision.dto.PersonalInfoProvisionDTO;
import com.app.kokonut.personalInfoProvision.service.PersonalInfoProvisionService;
import com.app.kokonut.personalInfoProvision.vo.PersonalInfoProvisionQueryVO;
import com.app.kokonut.personalInfoProvision.vo.PersonalInfoProvisionUpdateVO;
import com.app.kokonut.personalInfoProvision.vo.PersonalInfoProvisionVO;
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
@RequestMapping("/personalInfoProvision")
public class PersonalInfoProvisionController {

    @Autowired
    private PersonalInfoProvisionService personalInfoProvisionService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody PersonalInfoProvisionVO vO) {
        return personalInfoProvisionService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        personalInfoProvisionService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody PersonalInfoProvisionUpdateVO vO) {
        personalInfoProvisionService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public PersonalInfoProvisionDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return personalInfoProvisionService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<PersonalInfoProvisionDTO> query(@Valid PersonalInfoProvisionQueryVO vO) {
        return personalInfoProvisionService.query(vO);
    }
}

package com.app.kokonut.personalInfoProvisionHistory.controller;

import com.app.kokonut.personalInfoProvisionHistory.dto.PersonalInfoProvisionHistoryDTO;
import com.app.kokonut.personalInfoProvisionHistory.service.PersonalInfoProvisionHistoryService;
import com.app.kokonut.personalInfoProvisionHistory.vo.PersonalInfoProvisionHistoryQueryVO;
import com.app.kokonut.personalInfoProvisionHistory.vo.PersonalInfoProvisionHistoryUpdateVO;
import com.app.kokonut.personalInfoProvisionHistory.vo.PersonalInfoProvisionHistoryVO;
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
@RequestMapping("/personalInfoProvisionHistory")
public class PersonalInfoProvisionHistoryController {

    @Autowired
    private PersonalInfoProvisionHistoryService personalInfoProvisionHistoryService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody PersonalInfoProvisionHistoryVO vO) {
        return personalInfoProvisionHistoryService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        personalInfoProvisionHistoryService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody PersonalInfoProvisionHistoryUpdateVO vO) {
        personalInfoProvisionHistoryService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public PersonalInfoProvisionHistoryDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return personalInfoProvisionHistoryService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<PersonalInfoProvisionHistoryDTO> query(@Valid PersonalInfoProvisionHistoryQueryVO vO) {
        return personalInfoProvisionHistoryService.query(vO);
    }
}

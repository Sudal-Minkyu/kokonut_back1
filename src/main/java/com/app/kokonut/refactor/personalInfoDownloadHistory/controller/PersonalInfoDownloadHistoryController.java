package com.app.kokonut.refactor.personalInfoDownloadHistory.controller;

import com.app.kokonut.refactor.personalInfoDownloadHistory.dto.PersonalInfoDownloadHistoryDTO;
import com.app.kokonut.refactor.personalInfoDownloadHistory.service.PersonalInfoDownloadHistoryService;
import com.app.kokonut.refactor.personalInfoDownloadHistory.vo.PersonalInfoDownloadHistoryQueryVO;
import com.app.kokonut.refactor.personalInfoDownloadHistory.vo.PersonalInfoDownloadHistoryUpdateVO;
import com.app.kokonut.refactor.personalInfoDownloadHistory.vo.PersonalInfoDownloadHistoryVO;
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
@RequestMapping("/personalInfoDownloadHistory")
public class PersonalInfoDownloadHistoryController {

    @Autowired
    private PersonalInfoDownloadHistoryService personalInfoDownloadHistoryService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody PersonalInfoDownloadHistoryVO vO) {
        return personalInfoDownloadHistoryService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        personalInfoDownloadHistoryService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody PersonalInfoDownloadHistoryUpdateVO vO) {
        personalInfoDownloadHistoryService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public PersonalInfoDownloadHistoryDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return personalInfoDownloadHistoryService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<PersonalInfoDownloadHistoryDTO> query(@Valid PersonalInfoDownloadHistoryQueryVO vO) {
        return personalInfoDownloadHistoryService.query(vO);
    }
}

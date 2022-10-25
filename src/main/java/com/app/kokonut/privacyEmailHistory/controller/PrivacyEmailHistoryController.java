package com.app.kokonut.privacyEmailHistory.controller;

import com.app.kokonut.privacyEmailHistory.dto.PrivacyEmailHistoryDTO;
import com.app.kokonut.privacyEmailHistory.service.PrivacyEmailHistoryService;
import com.app.kokonut.privacyEmailHistory.vo.PrivacyEmailHistoryQueryVO;
import com.app.kokonut.privacyEmailHistory.vo.PrivacyEmailHistoryUpdateVO;
import com.app.kokonut.privacyEmailHistory.vo.PrivacyEmailHistoryVO;
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
@RequestMapping("/privacyEmailHistory")
public class PrivacyEmailHistoryController {

    @Autowired
    private PrivacyEmailHistoryService privacyEmailHistoryService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody PrivacyEmailHistoryVO vO) {
        return privacyEmailHistoryService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        privacyEmailHistoryService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody PrivacyEmailHistoryUpdateVO vO) {
        privacyEmailHistoryService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public PrivacyEmailHistoryDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return privacyEmailHistoryService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<PrivacyEmailHistoryDTO> query(@Valid PrivacyEmailHistoryQueryVO vO) {
        return privacyEmailHistoryService.query(vO);
    }
}

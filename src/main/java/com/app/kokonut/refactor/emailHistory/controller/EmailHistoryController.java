package com.app.kokonut.refactor.emailHistory.controller;

import com.app.kokonut.refactor.emailHistory.dto.EmailHistoryDTO;
import com.app.kokonut.refactor.emailHistory.service.EmailHistoryService;
import com.app.kokonut.refactor.emailHistory.vo.EmailHistoryQueryVO;
import com.app.kokonut.refactor.emailHistory.vo.EmailHistoryUpdateVO;
import com.app.kokonut.refactor.emailHistory.vo.EmailHistoryVO;
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
@RequestMapping("/emailHistory")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailHistoryService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody EmailHistoryVO vO) {
        return emailHistoryService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        emailHistoryService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody EmailHistoryUpdateVO vO) {
        emailHistoryService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public EmailHistoryDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return emailHistoryService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<EmailHistoryDTO> query(@Valid EmailHistoryQueryVO vO) {
        return emailHistoryService.query(vO);
    }
}

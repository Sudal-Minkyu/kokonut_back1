package com.app.kokonut.log4jHistory.controller;

import com.app.kokonut.log4jHistory.dto.Log4jHistoryDTO;
import com.app.kokonut.log4jHistory.service.Log4jHistoryService;
import com.app.kokonut.log4jHistory.vo.Log4jHistoryQueryVO;
import com.app.kokonut.log4jHistory.vo.Log4jHistoryUpdateVO;
import com.app.kokonut.log4jHistory.vo.Log4jHistoryVO;
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
@RequestMapping("/log4jHistory")
public class Log4jHistoryController {

    @Autowired
    private Log4jHistoryService log4jHistoryService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody Log4jHistoryVO vO) {
        return log4jHistoryService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        log4jHistoryService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody Log4jHistoryUpdateVO vO) {
        log4jHistoryService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public Log4jHistoryDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return log4jHistoryService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<Log4jHistoryDTO> query(@Valid Log4jHistoryQueryVO vO) {
        return log4jHistoryService.query(vO);
    }
}

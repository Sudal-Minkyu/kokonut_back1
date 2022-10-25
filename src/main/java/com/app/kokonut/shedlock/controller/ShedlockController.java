package com.app.kokonut.shedlock.controller;

import com.app.kokonut.shedlock.dto.ShedlockDTO;
import com.app.kokonut.shedlock.service.ShedlockService;
import com.app.kokonut.shedlock.vo.ShedlockQueryVO;
import com.app.kokonut.shedlock.vo.ShedlockUpdateVO;
import com.app.kokonut.shedlock.vo.ShedlockVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(tags = "스케줄잠금")
@Validated
@RestController
@RequestMapping("/shedlock")
public class ShedlockController {

    @Autowired
    private ShedlockService shedlockService;

    @PostMapping
    @ApiOperation("Save 스케줄잠금")
    public String save(@Valid @RequestBody ShedlockVO vO) {
        return shedlockService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete 스케줄잠금")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        shedlockService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update 스케줄잠금")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody ShedlockUpdateVO vO) {
        shedlockService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID 스케줄잠금")
    public ShedlockDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return shedlockService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query 스케줄잠금")
    public Page<ShedlockDTO> query(@Valid ShedlockQueryVO vO) {
        return shedlockService.query(vO);
    }
}

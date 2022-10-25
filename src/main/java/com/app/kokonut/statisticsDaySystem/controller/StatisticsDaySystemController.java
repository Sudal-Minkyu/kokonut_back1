package com.app.kokonut.statisticsDaySystem.controller;

import com.app.kokonut.statisticsDaySystem.dto.StatisticsDaySystemDTO;
import com.app.kokonut.statisticsDaySystem.service.StatisticsDaySystemService;
import com.app.kokonut.statisticsDaySystem.vo.StatisticsDaySystemQueryVO;
import com.app.kokonut.statisticsDaySystem.vo.StatisticsDaySystemUpdateVO;
import com.app.kokonut.statisticsDaySystem.vo.StatisticsDaySystemVO;
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
@RequestMapping("/statisticsDaySystem")
public class StatisticsDaySystemController {

    @Autowired
    private StatisticsDaySystemService statisticsDaySystemService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody StatisticsDaySystemVO vO) {
        return statisticsDaySystemService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        statisticsDaySystemService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody StatisticsDaySystemUpdateVO vO) {
        statisticsDaySystemService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public StatisticsDaySystemDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return statisticsDaySystemService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<StatisticsDaySystemDTO> query(@Valid StatisticsDaySystemQueryVO vO) {
        return statisticsDaySystemService.query(vO);
    }
}

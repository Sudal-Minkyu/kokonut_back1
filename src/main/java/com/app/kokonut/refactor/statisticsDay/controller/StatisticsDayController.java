package com.app.kokonut.refactor.statisticsDay.controller;

import com.app.kokonut.refactor.statisticsDay.dto.StatisticsDayDTO;
import com.app.kokonut.refactor.statisticsDay.service.StatisticsDayService;
import com.app.kokonut.refactor.statisticsDay.vo.StatisticsDayQueryVO;
import com.app.kokonut.refactor.statisticsDay.vo.StatisticsDayUpdateVO;
import com.app.kokonut.refactor.statisticsDay.vo.StatisticsDayVO;
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
@RequestMapping("/statisticsDay")
public class StatisticsDayController {

    @Autowired
    private StatisticsDayService statisticsDayService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody StatisticsDayVO vO) {
        return statisticsDayService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        statisticsDayService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody StatisticsDayUpdateVO vO) {
        statisticsDayService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public StatisticsDayDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return statisticsDayService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<StatisticsDayDTO> query(@Valid StatisticsDayQueryVO vO) {
        return statisticsDayService.query(vO);
    }
}

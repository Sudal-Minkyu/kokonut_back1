package com.app.kokonut.activityHistory.controller;

import com.app.kokonut.activityHistory.dto.ActivityHistoryDTO;
import com.app.kokonut.activityHistory.service.ActivityHistoryService;
import com.app.kokonut.activityHistory.vo.ActivityHistoryQueryVO;
import com.app.kokonut.activityHistory.vo.ActivityHistoryUpdateVO;
import com.app.kokonut.activityHistory.vo.ActivityHistoryVO;
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
@RequestMapping("/activityHistory")
public class ActivityHistoryController {

    @Autowired
    private ActivityHistoryService activityHistoryService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody ActivityHistoryVO vO) {
        return activityHistoryService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        activityHistoryService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody ActivityHistoryUpdateVO vO) {
        activityHistoryService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public ActivityHistoryDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return activityHistoryService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<ActivityHistoryDTO> query(@Valid ActivityHistoryQueryVO vO) {
        return activityHistoryService.query(vO);
    }
}

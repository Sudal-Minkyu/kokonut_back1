package com.app.kokonut.activity.controller;

import com.app.kokonut.activity.vo.ActivityQueryVO;
import com.app.kokonut.activity.vo.ActivityUpdateVO;
import com.app.kokonut.activity.vo.ActivityVO;
import com.app.kokonut.activity.dto.ActivityDTO;
import com.app.kokonut.activity.service.ActivityService;
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
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody ActivityVO vO) {
        return activityService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        activityService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody ActivityUpdateVO vO) {
        activityService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public ActivityDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return activityService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<ActivityDTO> query(@Valid ActivityQueryVO vO) {
        return activityService.query(vO);
    }
}

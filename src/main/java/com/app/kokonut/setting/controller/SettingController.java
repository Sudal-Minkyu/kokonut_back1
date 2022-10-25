package com.app.kokonut.setting.controller;

import com.app.kokonut.setting.dto.SettingDTO;
import com.app.kokonut.setting.service.SettingService;
import com.app.kokonut.setting.vo.SettingQueryVO;
import com.app.kokonut.setting.vo.SettingUpdateVO;
import com.app.kokonut.setting.vo.SettingVO;
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
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody SettingVO vO) {
        return settingService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        settingService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody SettingUpdateVO vO) {
        settingService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public SettingDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return settingService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<SettingDTO> query(@Valid SettingQueryVO vO) {
        return settingService.query(vO);
    }
}

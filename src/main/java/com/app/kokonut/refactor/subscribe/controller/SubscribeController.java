package com.app.kokonut.refactor.subscribe.controller;

import com.app.kokonut.refactor.subscribe.service.SubscribeService;
import com.app.kokonut.refactor.subscribe.dto.SubscribeDTO;
import com.app.kokonut.refactor.subscribe.vo.SubscribeQueryVO;
import com.app.kokonut.refactor.subscribe.vo.SubscribeUpdateVO;
import com.app.kokonut.refactor.subscribe.vo.SubscribeVO;
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
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody SubscribeVO vO) {
        return subscribeService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        subscribeService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody SubscribeUpdateVO vO) {
        subscribeService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public SubscribeDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return subscribeService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<SubscribeDTO> query(@Valid SubscribeQueryVO vO) {
        return subscribeService.query(vO);
    }
}

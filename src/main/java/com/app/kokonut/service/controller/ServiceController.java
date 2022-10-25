package com.app.kokonut.service.controller;

import com.app.kokonut.service.dto.ServiceDTO;
import com.app.kokonut.service.service.ServiceService;
import com.app.kokonut.service.vo.ServiceQueryVO;
import com.app.kokonut.service.vo.ServiceUpdateVO;
import com.app.kokonut.service.vo.ServiceVO;
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
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody ServiceVO vO) {
        return serviceService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        serviceService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody ServiceUpdateVO vO) {
        serviceService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public ServiceDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return serviceService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<ServiceDTO> query(@Valid ServiceQueryVO vO) {
        return serviceService.query(vO);
    }
}

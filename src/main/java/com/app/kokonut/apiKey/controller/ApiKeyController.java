package com.app.kokonut.apiKey.controller;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.service.ApiKeyService;
import com.app.kokonut.apiKey.vo.ApiKeyQueryVO;
import com.app.kokonut.apiKey.vo.ApiKeyUpdateVO;
import com.app.kokonut.apiKey.vo.ApiKeyVO;
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
@RequestMapping("/apiKey")
public class ApiKeyController {

    @Autowired
    private ApiKeyService apiKeyService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody ApiKeyVO vO) {
        return apiKeyService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        apiKeyService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody ApiKeyUpdateVO vO) {
        apiKeyService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public ApiKeyKeyDto getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return apiKeyService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<ApiKeyKeyDto> query(@Valid ApiKeyQueryVO vO) {
        return apiKeyService.query(vO);
    }
}

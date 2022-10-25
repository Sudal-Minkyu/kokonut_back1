package com.app.kokonut.awsKmsHistory.controller;

import com.app.kokonut.awsKmsHistory.dto.AwsKmsHistoryDTO;
import com.app.kokonut.awsKmsHistory.service.AwsKmsHistoryService;
import com.app.kokonut.awsKmsHistory.vo.AwsKmsHistoryQueryVO;
import com.app.kokonut.awsKmsHistory.vo.AwsKmsHistoryUpdateVO;
import com.app.kokonut.awsKmsHistory.vo.AwsKmsHistoryVO;
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
@RequestMapping("/awsKmsHistory")
public class AwsKmsHistoryController {

    @Autowired
    private AwsKmsHistoryService awsKmsHistoryService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody AwsKmsHistoryVO vO) {
        return awsKmsHistoryService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        awsKmsHistoryService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody AwsKmsHistoryUpdateVO vO) {
        awsKmsHistoryService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public AwsKmsHistoryDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return awsKmsHistoryService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<AwsKmsHistoryDTO> query(@Valid AwsKmsHistoryQueryVO vO) {
        return awsKmsHistoryService.query(vO);
    }
}

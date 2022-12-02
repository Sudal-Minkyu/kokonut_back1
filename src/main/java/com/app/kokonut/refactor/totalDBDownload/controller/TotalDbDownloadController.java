package com.app.kokonut.refactor.totalDBDownload.controller;

import com.app.kokonut.refactor.totalDBDownload.service.TotalDbDownloadService;
import com.app.kokonut.refactor.totalDBDownload.dto.TotalDbDownloadDTO;
import com.app.kokonut.refactor.totalDBDownload.vo.TotalDbDownloadQueryVO;
import com.app.kokonut.refactor.totalDBDownload.vo.TotalDbDownloadUpdateVO;
import com.app.kokonut.refactor.totalDBDownload.vo.TotalDbDownloadVO;
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
@RequestMapping("/totalDbDownload")
public class TotalDbDownloadController {

    @Autowired
    private TotalDbDownloadService totalDbDownloadService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody TotalDbDownloadVO vO) {
        return totalDbDownloadService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        totalDbDownloadService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody TotalDbDownloadUpdateVO vO) {
        totalDbDownloadService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public TotalDbDownloadDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return totalDbDownloadService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<TotalDbDownloadDTO> query(@Valid TotalDbDownloadQueryVO vO) {
        return totalDbDownloadService.query(vO);
    }
}

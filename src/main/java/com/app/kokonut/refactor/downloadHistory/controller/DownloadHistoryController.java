package com.app.kokonut.refactor.downloadHistory.controller;

import com.app.kokonut.refactor.downloadHistory.dto.DownloadHistoryDTO;
import com.app.kokonut.refactor.downloadHistory.service.DownloadHistoryService;
import com.app.kokonut.refactor.downloadHistory.vo.DownloadHistoryQueryVO;
import com.app.kokonut.refactor.downloadHistory.vo.DownloadHistoryUpdateVO;
import com.app.kokonut.refactor.downloadHistory.vo.DownloadHistoryVO;
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
@RequestMapping("/downloadHistory")
public class DownloadHistoryController {

    @Autowired
    private DownloadHistoryService downloadHistoryService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody DownloadHistoryVO vO) {
        return downloadHistoryService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        downloadHistoryService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody DownloadHistoryUpdateVO vO) {
        downloadHistoryService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public DownloadHistoryDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return downloadHistoryService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<DownloadHistoryDTO> query(@Valid DownloadHistoryQueryVO vO) {
        return downloadHistoryService.query(vO);
    }
}

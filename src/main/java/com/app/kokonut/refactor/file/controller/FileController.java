package com.app.kokonut.refactor.file.controller;

import com.app.kokonut.refactor.file.dto.FileDTO;
import com.app.kokonut.refactor.file.service.FileService;
import com.app.kokonut.refactor.file.vo.FileQueryVO;
import com.app.kokonut.refactor.file.vo.FileUpdateVO;
import com.app.kokonut.refactor.file.vo.FileVO;
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
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody FileVO vO) {
        return fileService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        fileService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody FileUpdateVO vO) {
        fileService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public FileDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return fileService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<FileDTO> query(@Valid FileQueryVO vO) {
        return fileService.query(vO);
    }
}

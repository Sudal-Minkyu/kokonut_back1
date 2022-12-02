package com.app.kokonut.refactor.revisedDocument.controller;

import com.app.kokonut.refactor.revisedDocument.dto.RevisedDocumentDTO;
import com.app.kokonut.refactor.revisedDocument.service.RevisedDocumentService;
import com.app.kokonut.refactor.revisedDocument.vo.RevisedDocumentVO;
import com.app.kokonut.refactor.revisedDocument.vo.RevisedDocumentQueryVO;
import com.app.kokonut.refactor.revisedDocument.vo.RevisedDocumentUpdateVO;
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
@RequestMapping("/revisedDocument")
public class RevisedDocumentController {

    @Autowired
    private RevisedDocumentService revisedDocumentService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody RevisedDocumentVO vO) {
        return revisedDocumentService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        revisedDocumentService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody RevisedDocumentUpdateVO vO) {
        revisedDocumentService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public RevisedDocumentDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return revisedDocumentService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<RevisedDocumentDTO> query(@Valid RevisedDocumentQueryVO vO) {
        return revisedDocumentService.query(vO);
    }
}

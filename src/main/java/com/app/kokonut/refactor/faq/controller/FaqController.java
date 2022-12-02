package com.app.kokonut.refactor.faq.controller;

import com.app.kokonut.refactor.faq.dto.FaqDTO;
import com.app.kokonut.refactor.faq.service.FaqService;
import com.app.kokonut.refactor.faq.vo.FaqQueryVO;
import com.app.kokonut.refactor.faq.vo.FaqUpdateVO;
import com.app.kokonut.refactor.faq.vo.FaqVO;
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
@RequestMapping("/faq")
public class FaqController {

    @Autowired
    private FaqService faqService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody FaqVO vO) {
        return faqService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        faqService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody FaqUpdateVO vO) {
        faqService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public FaqDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return faqService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<FaqDTO> query(@Valid FaqQueryVO vO) {
        return faqService.query(vO);
    }
}

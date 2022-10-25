package com.app.kokonut.qna.controller;

import com.app.kokonut.qna.dto.QnaDTO;
import com.app.kokonut.qna.service.QnaService;
import com.app.kokonut.qna.vo.QnaQueryVO;
import com.app.kokonut.qna.vo.QnaUpdateVO;
import com.app.kokonut.qna.vo.QnaVO;
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
@RequestMapping("/qna")
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody QnaVO vO) {
        return qnaService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        qnaService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody QnaUpdateVO vO) {
        qnaService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public QnaDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return qnaService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<QnaDTO> query(@Valid QnaQueryVO vO) {
        return qnaService.query(vO);
    }
}

package com.app.kokonut.notice.controller;

import com.app.kokonut.notice.dto.NoticeDTO;
import com.app.kokonut.notice.service.NoticeService;
import com.app.kokonut.notice.vo.NoticeQueryVO;
import com.app.kokonut.notice.vo.NoticeUpdateVO;
import com.app.kokonut.notice.vo.NoticeVO;
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
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody NoticeVO vO) {
        return noticeService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        noticeService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody NoticeUpdateVO vO) {
        noticeService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public NoticeDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return noticeService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<NoticeDTO> query(@Valid NoticeQueryVO vO) {
        return noticeService.query(vO);
    }
}

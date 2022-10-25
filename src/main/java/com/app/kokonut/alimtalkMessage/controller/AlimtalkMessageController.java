package com.app.kokonut.alimtalkMessage.controller;

import com.app.kokonut.alimtalkMessage.dto.AlimtalkMessageDTO;
import com.app.kokonut.alimtalkMessage.service.AlimtalkMessageService;
import com.app.kokonut.alimtalkMessage.vo.AlimtalkMessageQueryVO;
import com.app.kokonut.alimtalkMessage.vo.AlimtalkMessageUpdateVO;
import com.app.kokonut.alimtalkMessage.vo.AlimtalkMessageVO;
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
@RequestMapping("/alimtalkMessage")
public class AlimtalkMessageController {

    @Autowired
    private AlimtalkMessageService alimtalkMessageService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody AlimtalkMessageVO vO) {
        return alimtalkMessageService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        alimtalkMessageService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody AlimtalkMessageUpdateVO vO) {
        alimtalkMessageService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public AlimtalkMessageDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return alimtalkMessageService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<AlimtalkMessageDTO> query(@Valid AlimtalkMessageQueryVO vO) {
        return alimtalkMessageService.query(vO);
    }
}

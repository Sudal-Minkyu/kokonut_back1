package com.app.kokonut.alimtalkMessageRecipient.controller;

import com.app.kokonut.alimtalkMessageRecipient.dto.AlimtalkMessageRecipientDTO;
import com.app.kokonut.alimtalkMessageRecipient.service.AlimtalkMessageRecipientService;
import com.app.kokonut.alimtalkMessageRecipient.vo.AlimtalkMessageRecipientQueryVO;
import com.app.kokonut.alimtalkMessageRecipient.vo.AlimtalkMessageRecipientUpdateVO;
import com.app.kokonut.alimtalkMessageRecipient.vo.AlimtalkMessageRecipientVO;
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
@RequestMapping("/alimtalkMessageRecipient")
public class AlimtalkMessageRecipientController {

    @Autowired
    private AlimtalkMessageRecipientService alimtalkMessageRecipientService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody AlimtalkMessageRecipientVO vO) {
        return alimtalkMessageRecipientService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        alimtalkMessageRecipientService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody AlimtalkMessageRecipientUpdateVO vO) {
        alimtalkMessageRecipientService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public AlimtalkMessageRecipientDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return alimtalkMessageRecipientService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<AlimtalkMessageRecipientDTO> query(@Valid AlimtalkMessageRecipientQueryVO vO) {
        return alimtalkMessageRecipientService.query(vO);
    }
}

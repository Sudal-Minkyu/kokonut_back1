package com.app.kokonut.addressBook.controller;

import com.app.kokonut.addressBook.dto.AddressBookDTO;
import com.app.kokonut.addressBook.service.AddressBookService;
import com.app.kokonut.addressBook.vo.AddressBookQueryVO;
import com.app.kokonut.addressBook.vo.AddressBookUpdateVO;
import com.app.kokonut.addressBook.vo.AddressBookVO;
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
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody AddressBookVO vO) {
        return addressBookService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        addressBookService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody AddressBookUpdateVO vO) {
        addressBookService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public AddressBookDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return addressBookService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<AddressBookDTO> query(@Valid AddressBookQueryVO vO) {
        return addressBookService.query(vO);
    }
}

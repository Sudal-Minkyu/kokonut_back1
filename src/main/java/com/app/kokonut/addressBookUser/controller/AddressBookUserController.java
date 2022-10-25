package com.app.kokonut.addressBookUser.controller;

import com.app.kokonut.addressBookUser.dto.AddressBookUserDTO;
import com.app.kokonut.addressBookUser.service.AddressBookUserService;
import com.app.kokonut.addressBookUser.vo.AddressBookUserQueryVO;
import com.app.kokonut.addressBookUser.vo.AddressBookUserUpdateVO;
import com.app.kokonut.addressBookUser.vo.AddressBookUserVO;
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
@RequestMapping("/addressBookUser")
public class AddressBookUserController {

    @Autowired
    private AddressBookUserService addressBookUserService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody AddressBookUserVO vO) {
        return addressBookUserService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        addressBookUserService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody AddressBookUserUpdateVO vO) {
        addressBookUserService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public AddressBookUserDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return addressBookUserService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<AddressBookUserDTO> query(@Valid AddressBookUserQueryVO vO) {
        return addressBookUserService.query(vO);
    }
}

package com.app.kokonut.friendtalkMessageRecipient.controller;

import com.app.kokonut.friendtalkMessageRecipient.dto.FriendtalkMessageRecipientDTO;
import com.app.kokonut.friendtalkMessageRecipient.service.FriendtalkMessageRecipientService;
import com.app.kokonut.friendtalkMessageRecipient.vo.FriendtalkMessageRecipientQueryVO;
import com.app.kokonut.friendtalkMessageRecipient.vo.FriendtalkMessageRecipientUpdateVO;
import com.app.kokonut.friendtalkMessageRecipient.vo.FriendtalkMessageRecipientVO;
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
@RequestMapping("/friendtalkMessageRecipient")
public class FriendtalkMessageRecipientController {

    @Autowired
    private FriendtalkMessageRecipientService friendtalkMessageRecipientService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody FriendtalkMessageRecipientVO vO) {
        return friendtalkMessageRecipientService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        friendtalkMessageRecipientService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody FriendtalkMessageRecipientUpdateVO vO) {
        friendtalkMessageRecipientService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public FriendtalkMessageRecipientDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return friendtalkMessageRecipientService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<FriendtalkMessageRecipientDTO> query(@Valid FriendtalkMessageRecipientQueryVO vO) {
        return friendtalkMessageRecipientService.query(vO);
    }
}

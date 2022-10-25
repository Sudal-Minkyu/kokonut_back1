package com.app.kokonut.friendtalkMessage.controller;

import com.app.kokonut.friendtalkMessage.dto.FriendtalkMessageDTO;
import com.app.kokonut.friendtalkMessage.service.FriendtalkMessageService;
import com.app.kokonut.friendtalkMessage.vo.FriendtalkMessageQueryVO;
import com.app.kokonut.friendtalkMessage.vo.FriendtalkMessageUpdateVO;
import com.app.kokonut.friendtalkMessage.vo.FriendtalkMessageVO;
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
@RequestMapping("/friendtalkMessage")
public class FriendtalkMessageController {

    @Autowired
    private FriendtalkMessageService friendtalkMessageService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody FriendtalkMessageVO vO) {
        return friendtalkMessageService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        friendtalkMessageService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody FriendtalkMessageUpdateVO vO) {
        friendtalkMessageService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public FriendtalkMessageDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return friendtalkMessageService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<FriendtalkMessageDTO> query(@Valid FriendtalkMessageQueryVO vO) {
        return friendtalkMessageService.query(vO);
    }
}

package com.app.kokonut.kakaoChannel.controller;

import com.app.kokonut.kakaoChannel.dto.KakaoChannelDTO;
import com.app.kokonut.kakaoChannel.service.KakaoChannelService;
import com.app.kokonut.kakaoChannel.vo.KakaoChannelQueryVO;
import com.app.kokonut.kakaoChannel.vo.KakaoChannelUpdateVO;
import com.app.kokonut.kakaoChannel.vo.KakaoChannelVO;
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
@RequestMapping("/kakaoChannel")
public class KakaoChannelController {

    @Autowired
    private KakaoChannelService kakaoChannelService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody KakaoChannelVO vO) {
        return kakaoChannelService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        kakaoChannelService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody KakaoChannelUpdateVO vO) {
        kakaoChannelService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public KakaoChannelDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return kakaoChannelService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<KakaoChannelDTO> query(@Valid KakaoChannelQueryVO vO) {
        return kakaoChannelService.query(vO);
    }
}

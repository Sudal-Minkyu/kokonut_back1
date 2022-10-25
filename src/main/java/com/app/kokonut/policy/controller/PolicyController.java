package com.app.kokonut.policy.controller;

import com.app.kokonut.policy.dto.PolicyDTO;
import com.app.kokonut.policy.service.PolicyService;
import com.app.kokonut.policy.vo.PolicyQueryVO;
import com.app.kokonut.policy.vo.PolicyUpdateVO;
import com.app.kokonut.policy.vo.PolicyVO;
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
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody PolicyVO vO) {
        return policyService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        policyService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody PolicyUpdateVO vO) {
        policyService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public PolicyDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return policyService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<PolicyDTO> query(@Valid PolicyQueryVO vO) {
        return policyService.query(vO);
    }
}

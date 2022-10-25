package com.app.kokonut.payment.controller;

import com.app.kokonut.payment.dto.PaymentDTO;
import com.app.kokonut.payment.service.PaymentService;
import com.app.kokonut.payment.vo.PaymentQueryVO;
import com.app.kokonut.payment.vo.PaymentUpdateVO;
import com.app.kokonut.payment.vo.PaymentVO;
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
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody PaymentVO vO) {
        return paymentService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        paymentService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody PaymentUpdateVO vO) {
        paymentService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public PaymentDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return paymentService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<PaymentDTO> query(@Valid PaymentQueryVO vO) {
        return paymentService.query(vO);
    }
}

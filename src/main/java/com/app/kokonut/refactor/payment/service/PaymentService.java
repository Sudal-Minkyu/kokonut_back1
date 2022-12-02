package com.app.kokonut.refactor.payment.service;

import com.app.kokonut.refactor.payment.dto.PaymentDTO;
import com.app.kokonut.refactor.payment.entity.Payment;
import com.app.kokonut.refactor.payment.repository.PaymentRepository;
import com.app.kokonut.refactor.payment.vo.PaymentQueryVO;
import com.app.kokonut.refactor.payment.vo.PaymentUpdateVO;
import com.app.kokonut.refactor.payment.vo.PaymentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Integer save(PaymentVO vO) {
        Payment bean = new Payment();
        BeanUtils.copyProperties(vO, bean);
        bean = paymentRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        paymentRepository.deleteById(id);
    }

    public void update(Integer id, PaymentUpdateVO vO) {
        Payment bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        paymentRepository.save(bean);
    }

    public PaymentDTO getById(Integer id) {
        Payment original = requireOne(id);
        return toDTO(original);
    }

    public Page<PaymentDTO> query(PaymentQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private PaymentDTO toDTO(Payment original) {
        PaymentDTO bean = new PaymentDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Payment requireOne(Integer id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

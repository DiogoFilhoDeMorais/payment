package com.natixis.payment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.natixis.payment.dto.PaymentRequest;
import com.natixis.payment.dto.PaymentResponse;
import com.natixis.payment.service.PaymentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        var payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> pay(@RequestBody @Valid PaymentRequest request) {
        var paymentRequest = paymentService.createPayment(request);
        return ResponseEntity.ok(paymentRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable Long id, @RequestBody @Valid PaymentRequest request) {
        var updatePayment = paymentService.UpdatePaymentData(id, request);
        return ResponseEntity.ok(updatePayment);
    }
}

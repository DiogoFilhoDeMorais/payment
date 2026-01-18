package com.natixis.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.natixis.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
}

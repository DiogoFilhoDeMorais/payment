package com.natixis.payment.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.natixis.payment.dto.PaymentRequest;
import com.natixis.payment.dto.PaymentResponse;
import com.natixis.payment.entity.Payment;
import com.natixis.payment.repository.PaymentRepository;


@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public PaymentResponse createPayment(PaymentRequest request) {
        var payment = new Payment(
                request.originAccount(),
                request.beneficiaryAccount(),
                request.amount(),
                BigDecimal.ZERO,
                LocalDate.now(),
                request.scheduledPayment()
        );
        this.calcFee(payment);
        var savedPayment = paymentRepository.save(payment);
        return toResponse(savedPayment);
    }

    private void calcFee(Payment payment) {
        long difDays = ChronoUnit.DAYS.between(payment.getScheduledDate(), payment.getCurrentDate());

        BigDecimal amount = payment.getAmount();
        var fee = BigDecimal.ZERO;

        if (amount.compareTo(new BigDecimal("1000.99")) <= 0) {
            if (difDays == 0) {
                fee = amount.multiply(new BigDecimal("0.03")).add(new BigDecimal("3.00"));
            } else {
                throw new IllegalArgumentException("Valores até 1000,99 só podem ser agendados para o mesmo dia.");
            }
        }

        else if (amount.compareTo(new BigDecimal("2000.00")) <= 0) {
            if (difDays >= 1 && difDays <= 10) {
                fee = amount.multiply(new BigDecimal("0.09")); 
            } else {
                throw new IllegalArgumentException("Valores entre 1001 e 2000 só podem ser agendados entre 1 e 10 dias.");
            }
        }

        else {
            if (difDays > 10 && difDays <= 20) fee = amount.multiply(new BigDecimal("0.082"));
            else if (difDays > 20 && difDays <= 30) fee = amount.multiply(new BigDecimal("0.069"));
            else if (difDays > 30 && difDays <= 40) fee = amount.multiply(new BigDecimal("0.047"));
            else if (difDays > 40) fee = amount.multiply(new BigDecimal("0.017"));
            else throw new IllegalArgumentException("Valores acima de 2000 só podem ser agendados após 10 dias.");
        }

        payment.setFee(fee.setScale(2, RoundingMode.HALF_UP));
    }

    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
            payment.getId(), 
            payment.getOriginAccount(), 
            payment.getBeneficiaryAccount(), 
            payment.getAmount(), 
            payment.getFee(), 
            payment.getCurrentDate(),
            payment.getScheduledDate()
        );
    }
}

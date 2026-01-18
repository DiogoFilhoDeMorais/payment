package com.natixis.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentResponse(
    Long id,
    String originAccount,
    String beneficiaryAccount,
    BigDecimal amount,
    BigDecimal fee,
    LocalDate payDate,
    LocalDate scheduledDate
) {}

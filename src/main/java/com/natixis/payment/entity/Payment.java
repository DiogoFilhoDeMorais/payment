package com.natixis.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originAccount;
    private String beneficiaryAccount;
    private BigDecimal amount;
    private BigDecimal fee;
    private LocalDate todayDate;
    private LocalDate scheduledDate;

    public Payment() {
    }

    public Payment(String originAccount, String beneficiaryAccount, BigDecimal amount, BigDecimal fee, LocalDate todayDate, LocalDate scheduledDate) {
        this.originAccount = originAccount;
        this.beneficiaryAccount = beneficiaryAccount;
        this.amount = amount;
        this.fee = fee;
        this.todayDate = todayDate;
        this.scheduledDate = scheduledDate;
    }

    public Long getId() {
        return id;
    }
    public String getOriginAccount() {
        return originAccount;
    }
    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public BigDecimal getFee() {
        return fee;
    }
    public LocalDate getTodayDate() {
        return todayDate;
    }
    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public void updatePaymentData(String originAccount, String beneficiaryAccount, BigDecimal amount, LocalDate scheduledDate) {
        this.originAccount = originAccount;
        this.beneficiaryAccount = beneficiaryAccount;
        this.amount = amount;
        this.scheduledDate = scheduledDate;
    }
}

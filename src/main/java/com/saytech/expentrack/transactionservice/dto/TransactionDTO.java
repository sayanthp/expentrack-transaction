package com.saytech.expentrack.transactionservice.dto;

import com.saytech.expentrack.transactionservice.enums.CategoryType;
import com.saytech.expentrack.transactionservice.enums.PaymentMode;
import com.saytech.expentrack.transactionservice.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;

    @NotNull
    private double amount;

    private String description;

    private CategoryType category;

    @NotNull
    private LocalDate date;

    @NotNull
    private TransactionType type;

    @NotNull
    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private PaymentMode paymentMode;

    private String merchant;

    public TransactionDTO() {}

    public TransactionDTO(Long id, double amount, String description, CategoryType category,
                          LocalDate date, TransactionType type, Long userId,
                          LocalDateTime createdAt, LocalDateTime updatedAt,
                          PaymentMode paymentMode, String merchant) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.type = type;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.paymentMode = paymentMode;
        this.merchant = merchant;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", date=" + date +
                ", type=" + type +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", paymentMode=" + paymentMode +
                ", merchant='" + merchant + '\'' +
                '}';
    }


}

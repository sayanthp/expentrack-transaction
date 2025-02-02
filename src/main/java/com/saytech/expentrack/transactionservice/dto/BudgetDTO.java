package com.saytech.expentrack.transactionservice.dto;

import com.saytech.expentrack.transactionservice.enums.CategoryType;
import java.util.Objects;

public class BudgetDTO {

    private Long id;
    private double amount;
    private Long userId;
    private CategoryType category;

    public BudgetDTO() {}

    public BudgetDTO(Long id, double amount, Long userId, CategoryType category) {
        this.id = id;
        this.amount = amount;
        this.userId = userId;
        this.category = category;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "BudgetDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", userId=" + userId +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BudgetDTO budgetDTO)) return false;
        return Double.compare(budgetDTO.amount, amount) == 0 &&
                id.equals(budgetDTO.id) &&
                userId.equals(budgetDTO.userId) &&
                category == budgetDTO.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, userId, category);
    }
}

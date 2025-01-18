package com.saytech.expentrack.transactionservice.dto;

import com.saytech.expentrack.transactionservice.enums.CategoryType;
import java.util.Objects;

public class BudgetDTO {

    private Long id;
    private double amount;  // Renamed to 'amount' to match the entity field
    private Long userId;
    private CategoryType category;

    // No-argument constructor
    public BudgetDTO() {}

    // Parameterized constructor
    public BudgetDTO(Long id, double amount, Long userId, CategoryType category) {  // Renamed 'limit' to 'amount'
        this.id = id;
        this.amount = amount;  // Correct field name
        this.userId = userId;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {  // Renamed to 'amount' to match the field
        return amount;
    }

    public void setAmount(double amount) {  // Renamed to 'amount' to match the field
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

    // Override toString
    @Override
    public String toString() {
        return "BudgetDTO{" +
                "id=" + id +
                ", amount=" + amount +  // Renamed to 'amount'
                ", userId=" + userId +
                ", category=" + category +
                '}';
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BudgetDTO)) return false;
        BudgetDTO budgetDTO = (BudgetDTO) o;
        return Double.compare(budgetDTO.amount, amount) == 0 &&  // Renamed to 'amount'
                id.equals(budgetDTO.id) &&
                userId.equals(budgetDTO.userId) &&
                category == budgetDTO.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, userId, category);  // Renamed to 'amount'
    }
}

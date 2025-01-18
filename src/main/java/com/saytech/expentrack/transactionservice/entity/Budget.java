package com.saytech.expentrack.transactionservice.entity;

import com.saytech.expentrack.transactionservice.enums.CategoryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(value = 0, message = "Amount must be a positive value")
    private double amount;  // Renamed to 'amount' for consistency

    @Enumerated(EnumType.STRING) // Store enum as a string
    @Column(nullable = false)
    private CategoryType category;

    @Column(nullable = false)
    private Long userId;

    // No-argument constructor
    public Budget() {}

    // Parameterized constructor
    public Budget(double amount, CategoryType category, Long userId) {  // Consistent with 'amount' field
        this.amount = amount;
        this.category = category;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;  // Changed to 'amount' for consistency
    }

    public void setAmount(double amount) {  // Changed to 'amount' for consistency
        this.amount = amount;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Override toString
    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", amount=" + amount +  // Changed to 'amount' for consistency
                ", category=" + category +
                ", userId=" + userId +
                '}';
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Budget)) return false;
        Budget budget = (Budget) o;
        return Double.compare(budget.amount, amount) == 0 &&  // Changed to 'amount' for consistency
                id.equals(budget.id) &&
                category == budget.category &&
                userId.equals(budget.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, category, userId);  // Changed to 'amount' for consistency
    }
}

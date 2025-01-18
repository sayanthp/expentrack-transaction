package com.saytech.expentrack.transactionservice.event;

import com.saytech.expentrack.transactionservice.entity.Budget;
import com.saytech.expentrack.transactionservice.enums.EventType;

public class BudgetExceededEvent extends Event {

    private final double exceededAmount;

    private final Budget budget;

    public BudgetExceededEvent(Budget budget, double exceededAmount) {
        super(EventType.BUDGET_EXCEEDED);
        this.exceededAmount = exceededAmount;
        this.budget = budget;
    }

    public double getExceededAmount() {
        return exceededAmount;
    }

    @Override
    public String toString() {
        return "BudgetExceededEvent{" +
                "timestamp=" + getTimestamp() +
                ", eventType=" + getEventType() +
                ", userId=" + budget.getUserId() +
                ", category='" + budget.getCategory() + '\'' +
                ", exceededAmount=" + exceededAmount +
                '}';
    }
}

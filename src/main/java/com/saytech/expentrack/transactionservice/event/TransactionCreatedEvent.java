package com.saytech.expentrack.transactionservice.event;

import com.saytech.expentrack.transactionservice.entity.Transaction;
import com.saytech.expentrack.transactionservice.enums.EventType;

public class TransactionCreatedEvent extends Event {

    private final Transaction transaction;

    public TransactionCreatedEvent(Transaction transaction) {
        super(EventType.TRANSACTION_CREATED);
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return "TransactionCreatedEvent{" +
                "timestamp=" + getTimestamp() +
                ", eventType=" + getEventType() +
                ", transactionId=" + transaction.getId() +
                ", amount=" + transaction.getAmount() +
                ", description='" + transaction.getDescription() + '\'' +
                '}';
    }
}

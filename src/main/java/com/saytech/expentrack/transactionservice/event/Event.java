package com.saytech.expentrack.transactionservice.event;

import com.saytech.expentrack.transactionservice.enums.EventType;

import java.time.LocalDateTime;

public abstract class Event {

    private final LocalDateTime timestamp;

    private final EventType eventType;

    protected Event(EventType eventType) {
        this.timestamp = LocalDateTime.now();
        this.eventType = eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public EventType getEventType() {
        return eventType;
    }
}

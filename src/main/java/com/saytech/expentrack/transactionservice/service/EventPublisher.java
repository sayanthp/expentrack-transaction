package com.saytech.expentrack.transactionservice.service;

import com.saytech.expentrack.transactionservice.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class EventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(EventPublisher.class);

    @Value("${kafka.topic.notification-topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    public void publishEvent(Event event) {

        if (event == null) {
            logger.error("Cannot publish null event");
            return;
        }

        CompletableFuture<SendResult<String, Event>> future = kafkaTemplate.send(topic, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                logger.error("Failed to publish event: {} due to: {}", event, ex.getMessage(), ex);
            } else {
                logger.info("Successfully published event: {} to topic: {} with offset: {}",
                        event, topic, result.getRecordMetadata().offset());
            }
        });
    }
}

package com.notificationservice.consumer;

import com.notificationservice.service.NotificationService;
import com.tpdev.events.AdCreatedEvent;
import com.tpdev.events.AdDeletedEvent;
import com.tpdev.events.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "user-events",
            groupId = "notification-group"
    )
    public void handleUserRegistered(UserRegisteredEvent event) {
        notificationService.handleUserRegistered(event);
    }

    @KafkaListener(
            topics = "ad-events",
            groupId = "notification-group"
    )
    public void handleAdCreated(AdCreatedEvent event) {
        notificationService.handleAdCreated(event);
    }

    @KafkaListener(
            topics = "ad-events",
            groupId = "notification-group"
    )
    public void handleAdDeleted(AdDeletedEvent event) {
        notificationService.handleAdDeleted(event);
    }
}

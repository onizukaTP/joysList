package com.notificationservice.service;

import com.tpdev.events.AdCreatedEvent;
import com.tpdev.events.AdDeletedEvent;
import com.tpdev.events.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final EmailService emailService;

    public void handleAdCreated (AdCreatedEvent event) {
        log.debug("Received AdCreatedEvent for ad {}", event.getTitle());
        emailService.sendAdCreatedEmail(event);
    }

    public void handleAdDeleted(AdDeletedEvent event) {
        log.debug("Received AdDeletedEvent for ad {}", event.getTitle());
        emailService.sendAdDeletedEmail(event);
    }

    public void handleUserRegistered(UserRegisteredEvent event) {
        log.debug("RECEIVED USER EVENT: {}", event);
        emailService.sendUserRegisteredEmail(event);
    }
}

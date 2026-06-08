package com.notificationservice.service;

import com.tpdev.events.AdCreatedEvent;
import com.tpdev.events.AdDeletedEvent;
import com.tpdev.events.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    public void sendAdCreatedEmail(AdCreatedEvent event) {
        log.info("Email sent for creation event for ad {}", event.getTitle());
    }

    public void sendAdDeletedEmail(AdDeletedEvent event) {
        log.info("Email send for deletion event for ad {}", event.getTitle());
    }

    public void sendUserRegisteredEmail(UserRegisteredEvent event) {
        log.info("Sending welcome email to {}", event.getEmail());
    }
}

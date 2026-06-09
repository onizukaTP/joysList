package com.notificationservice.service;

import com.tpdev.events.AdCreatedEvent;
import com.tpdev.events.AdDeletedEvent;
import com.tpdev.events.UserRegisteredEvent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendAdCreatedEmail(AdCreatedEvent event) {
        log.info("Email sent for creation event for ad {}", event.getTitle());
    }

    public void sendAdDeletedEmail(AdDeletedEvent event) {
        log.info("Email send for deletion event for ad {}", event.getTitle());
    }

    // just for logs
    public void printWelcomeMessage(UserRegisteredEvent event) {
        log.info("Welcome, {}", event.getEmail());
    }

    @Value("${spring.mail.username}")
    private String from;

    public void sendWelcomeEmail(UserRegisteredEvent event) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(from);

        mail.setTo(event.getEmail());

        mail.setSubject("Welcome to JoysList");

        mail.setText(
                "Hello "
                        + event.getUsername()
                        + ", your account has been created successfully."
        );
        try {
            mailSender.send(mail);
            log.info("Welcome email sent to {}", event.getEmail());
        } catch (Exception e) {
            log.error("Email failed", e);
        }
    }

    public void sendTestMail() {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(from);
        mail.setTo(from);
        mail.setSubject("Test");
        mail.setText("Hello");

        try {
            mailSender.send(mail);
            log.info("Mail Sent Successfully");
        } catch (MailException e) {
            log.error("Error Sending Mail: {}", e.getMessage(), e);
        }
    }
}

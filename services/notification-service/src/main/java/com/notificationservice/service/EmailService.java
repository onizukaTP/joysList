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
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo(from); // temporary — replace with user's email once user-service exists
        mail.setSubject("Your ad is live on JoysList!");
        mail.setText(
                "Hi " + event.getUserId() + ",\n\n" +
                        "Your ad \"" + event.getTitle() + "\" has been posted successfully " +
                        "under " + event.getCategory() + ".\n\n" +
                        "Thanks for using JoysList!"
        );

        try {
            mailSender.send(mail);
            log.info("Ad created email sent for adId={}", event.getAdId());
        } catch (MailException e) {
            log.error("Failed to send ad created email for adId={}: {}", event.getAdId(), e.getMessage());
        }
    }

    public void sendAdDeletedEmail(AdDeletedEvent event) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo(from); // same placeholder as AdCreated — replace with user email later
        mail.setSubject("Your ad has been removed from JoysList");
        mail.setText(
                "Hi,\n\n" +
                        "Your ad (ID: " + event.getAdId() + ") has been successfully removed.\n\n" +
                        "If you didn't do this, please contact support.\n\n" +
                        "— JoysList Team"
        );

        try {
            mailSender.send(mail);
            log.info("Ad deleted email sent for adId={}", event.getAdId());
        } catch (MailException e) {
            log.error("Failed to send ad deleted email for adId={}: {}", event.getAdId(), e.getMessage());
        }
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

    public void sendWelcomeEmail(String name, String mail) {

    }
}

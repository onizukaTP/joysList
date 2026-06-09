package com.notificationservice.controller;

import com.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("dev")
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Slf4j
public class MailTestController {

    private final EmailService emailService;

    @GetMapping("/mail")
    public ResponseEntity<String> test() {
        try {
            emailService.sendTestMail();
            return ResponseEntity.ok("Mail Sent Successfully");
        } catch (Exception e) {
            log.error("Failed to send email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
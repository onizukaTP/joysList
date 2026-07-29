package com.notificationservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.kafka.listener.auto-startup=false",
        "MAIL_USERNAME=test-user@example.com",
        "MAIL_PASSWORD=test-password"
})
class NotificationServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}

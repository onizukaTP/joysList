package com.tpdev.userService.kafka.producer;

import com.tpdev.events.UserProfileUpdatedEvent;
import com.tpdev.events.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventProducer {

    private static final String USER_REGISTERED_TOPIC   = "user-registered";
    private static final String USER_PROFILE_UPDATED_TOPIC = "user-profile-updated";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishUserRegistered(UserRegisteredEvent event) {
        log.info("Publishing UserRegisteredEvent for userId={}", event.getUserId());
        kafkaTemplate.send(USER_REGISTERED_TOPIC, String.valueOf(event.getUserId()), event);
    }

    public void publishProfileUpdated(UserProfileUpdatedEvent event) {
        log.info("Publishing UserProfileUpdatedEvent for userId={}", event.getUserId());
        kafkaTemplate.send(USER_PROFILE_UPDATED_TOPIC, String.valueOf(event.getUserId()), event);
    }
}

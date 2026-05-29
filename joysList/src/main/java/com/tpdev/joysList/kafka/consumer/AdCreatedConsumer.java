package com.tpdev.joysList.kafka.consumer;

import com.tpdev.joysList.dto.AdCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdCreatedConsumer {

    @KafkaListener(
            topics = "ad-events",
            groupId = "notification-group"
    )
    public void consume(AdCreatedEvent event) {
        log.info("Received event for ad {}", event.getAdId());
    }
}

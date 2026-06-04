package com.notificationservice.consumer;

import com.tpdev.events.AdCreatedEvent;
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
    public void consume(
            AdCreatedEvent event
    ) {

        log.info(
                "Sending email for ad {}",
                event.getAdId()
        );

    }
}

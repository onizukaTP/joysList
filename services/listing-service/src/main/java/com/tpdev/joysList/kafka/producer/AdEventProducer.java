package com.tpdev.joysList.kafka.producer;

import com.tpdev.joysList.dto.AdCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdEventProducer {

    private final KafkaTemplate<String, AdCreatedEvent> kafkaTemplate;

    public void publishAdCreated(AdCreatedEvent event) {
        kafkaTemplate.send("ad-events", event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Event sent to partition {} offset {}",
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("Failed to send event", ex);
                    }
                });
    }
}

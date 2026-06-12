package com.tpdev.joysList.kafka.producer;

import com.tpdev.events.AdCreatedEvent;
import com.tpdev.events.AdDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishAdCreated(AdCreatedEvent event) {
        kafkaTemplate.send("ad-created-events", event)
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

    public void publishAdDeleted(AdDeletedEvent event) {
        kafkaTemplate.send("ad-deleted-events", event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("AdDeletedEvent sent to partition {} offset {}",
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("Failed to send AdDeletedEvent", ex);
                    }
                });
    }
}

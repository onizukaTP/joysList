package com.tpdev.joysList.kafka.producer;

import com.tpdev.joysList.dto.AdCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdEventProducer {

    private final KafkaTemplate<String, AdCreatedEvent> kafkaTemplate;

    public void publishAdCreated(AdCreatedEvent event) {
        kafkaTemplate.send("ad-events", event);
    }
}

package org.example.serviceforcouriers.kafka;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener  {

    @org.springframework.kafka.annotation.KafkaListener(
        topics = "topic1",groupId = "group1")
    void listen(@Payload String message) {

    }
}

package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "item_challenge", groupId = "java_item")
    public void listenGroup(String message) {
        log.info("Received Message in group my-group: " + message);
    }
}

package com.iuh.fit.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventProducer {

    private static final String TOPIC = "user-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishUserRegistered(UserEvent event) {
        log.info("[USER-SERVICE] Publishing USER_REGISTERED event for userId={}, email={}",
                event.getUserId(), event.getEmail());
        kafkaTemplate.send(TOPIC, String.valueOf(event.getUserId()), event);
        log.info("[USER-SERVICE] Event published to topic '{}'", TOPIC);
    }
}

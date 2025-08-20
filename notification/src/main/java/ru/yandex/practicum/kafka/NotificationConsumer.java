package ru.yandex.practicum.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.NotificationDto;
import ru.yandex.practicum.service.NotificationService;

import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RetryableTopic
    @KafkaListener(topics = "${topic.notification}: notification")
    public void onMessage(@Header(RECEIVED_TOPIC) String receivedTopic,
                          @Payload NotificationDto notificationDto) {
        log.info("Received message from topic '{}': {}", receivedTopic, notificationDto);
        notificationService.notify(notificationDto);
    }
}

package ru.yandex.practicum.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.NotificationDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProducer {

    @Value("${topic.notification: notification}")
    private String topic;

    private final KafkaTemplate<String, NotificationDto> notificationKafkaTemplate;

    public void notify(NotificationDto notificationDto) {
        log.info("Sending notification to Kafka: {}", notificationDto);
        notificationKafkaTemplate.send(topic, notificationDto);
    }
}

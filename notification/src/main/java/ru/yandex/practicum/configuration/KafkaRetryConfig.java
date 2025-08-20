package ru.yandex.practicum.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import ru.yandex.practicum.dto.NotificationDto;

@Configuration
@EnableKafka
public class KafkaRetryConfig {

    @Value("${topic.notification}")
    private String topic;

    @Bean
    public RetryTopicConfiguration notificationRetryTopic(KafkaTemplate<String, NotificationDto> template) {
        return RetryTopicConfigurationBuilder
            .newInstance()
            .includeTopic(topic)
            .customBackoff(new CustomSleepingBackOffPolicy())
            .maxAttempts(5)
            .create(template);
    }
}

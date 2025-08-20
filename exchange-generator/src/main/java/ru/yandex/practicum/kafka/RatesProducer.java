package ru.yandex.practicum.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.RateDto;
import ru.yandex.practicum.service.ExchangeGeneratorService;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class RatesProducer {

    @Value("${topic.rates:rates}")
    private String topic;

    private final ExchangeGeneratorService exchangeGeneratorService;

    private final KafkaTemplate<String, RateDto> ratesKafkaTemplate;

    @Scheduled(fixedRate = 1000)
    public void sendRates() {
        log.trace("Sending rates to Kafka");
        ratesKafkaTemplate.send(topic, new RateDto(exchangeGeneratorService.getRates()));
    }
}

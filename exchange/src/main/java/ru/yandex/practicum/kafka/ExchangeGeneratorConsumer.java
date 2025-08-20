package ru.yandex.practicum.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.RateDto;
import ru.yandex.practicum.model.Rate;

import java.util.List;

import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeGeneratorConsumer {

    private RateDto currentRateDto;

    @KafkaListener(topics = "${topic.rates:rates}")
    public void onMessage(@Header(RECEIVED_TOPIC) String receivedTopic,
                          @Payload RateDto rateDto) {
        log.trace("Received message from topic '{}': {}", receivedTopic, rateDto);
        currentRateDto = rateDto;
    }

    public List<Rate> getRates() {
        log.trace("Returning current rates: {}", currentRateDto.rates());
        return currentRateDto.rates();
    }
}

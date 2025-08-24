package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.kafka.consumer.ExchangeGeneratorConsumer;
import ru.yandex.practicum.model.Rate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExGenServiceController {

    private final ExchangeGeneratorConsumer exchangeGeneratorConsumer;

    @GetMapping("/api/rates")
    public List<Rate> getRates() {
        return exchangeGeneratorConsumer.getRates();
    }
}

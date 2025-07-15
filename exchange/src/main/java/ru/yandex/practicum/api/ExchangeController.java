package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.ExchangeDto;
import ru.yandex.practicum.service.ExchangeService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping("/exchange")
    public Double exchange(@RequestBody ExchangeDto exchangeDto) {
        return exchangeService.exchange(exchangeDto);
    }
}

package ru.yandex.practicum.api;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.RateDto;
import ru.yandex.practicum.service.ExchangeGeneratorService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExchangeGeneratorController {

    private final ExchangeGeneratorService exchangeGeneratorService;

    @GetMapping("/api/rates")
    public RateDto rates() {
        return new RateDto(exchangeGeneratorService.getRates());
    }
}

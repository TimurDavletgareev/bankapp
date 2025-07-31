package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.CurrencyDto;
import ru.yandex.practicum.model.Currency;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
@Slf4j
public class CurrencyController {

    @GetMapping
    public CurrencyDto getAllCurrencies() {
        return new CurrencyDto(List.of(Currency.values()));
    }
}

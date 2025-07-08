package ru.yandex.practicum.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.model.Currency;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CurrencyDto {

    private final List<Currency> currency;
}

package ru.yandex.practicum.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.model.Rate;

import java.util.List;

@Data
@RequiredArgsConstructor
public class RateDto {

    private final List<Rate> rates;
}

package ru.yandex.practicum.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.ExchangeDto;
import ru.yandex.practicum.entity.ExchangeEntity;

@Component
@RequiredArgsConstructor
public class ExchangeEntityMapper {

    private final ObjectMapper objectMapper;

    public ExchangeEntity map(ExchangeDto exchangeDto) {
        return objectMapper.convertValue(exchangeDto, ExchangeEntity.class);
    }
}

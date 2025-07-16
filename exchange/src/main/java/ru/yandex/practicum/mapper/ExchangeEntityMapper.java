package ru.yandex.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.TransferDto;
import ru.yandex.practicum.entity.ExchangeEntity;

@Component
@RequiredArgsConstructor
public class ExchangeEntityMapper {

    public ExchangeEntity map(TransferDto transferDto) {
        ExchangeEntity exchangeEntity = new ExchangeEntity();
        exchangeEntity.setLogin(transferDto.getLogin());
        exchangeEntity.setFromCurrency(transferDto.getFromCurrency());
        exchangeEntity.setFromValue(transferDto.getFromValue());
        exchangeEntity.setToCurrency(transferDto.getToCurrency());
        exchangeEntity.setToValue(transferDto.getToValue());
        exchangeEntity.setToLogin(transferDto.getToLogin());
        return exchangeEntity;
    }
}

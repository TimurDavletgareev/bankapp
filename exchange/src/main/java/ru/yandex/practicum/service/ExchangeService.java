package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.TransferDto;
import ru.yandex.practicum.entity.ExchangeEntity;
import ru.yandex.practicum.error.exception.IncorrectRequestException;
import ru.yandex.practicum.kafka.ExchangeGeneratorConsumer;
import ru.yandex.practicum.mapper.ExchangeEntityMapper;
import ru.yandex.practicum.model.Rate;
import ru.yandex.practicum.repository.ExchangeRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeGeneratorConsumer exchangeGeneratorConsumer;
    private final ExchangeRepository exchangeRepository;
    private final ExchangeEntityMapper exchangeEntityMapper;

    public Double exchange(TransferDto transferDto) {
        log.info("Exchange {}", transferDto);
        Double toValue = convertValue(transferDto.getFromCurrency(), transferDto.getToCurrency(), transferDto.getFromValue());
        transferDto.setToValue(toValue);
        ExchangeEntity exchangeEntity = exchangeEntityMapper.map(transferDto);
        exchangeEntity = exchangeRepository.save(exchangeEntity);
        log.info("Exchange saved: {}", exchangeEntity);
        return toValue;
    }

    private Double convertValue(String fromCurrency, String toCurrency, Double value) {
        List<Rate> rates = exchangeGeneratorConsumer.getRates();
        Double valueInBaseCurrency = rates.stream()
                .filter(rate -> rate.getName().equals(fromCurrency))
                .map(rate -> rate.getValue() * value)
                .findFirst()
                .orElseThrow(() -> new IncorrectRequestException("Wrong fromCurrency name"));
        return rates.stream()
                .filter(rate -> rate.getName().equals(toCurrency))
                .map(rate -> valueInBaseCurrency / rate.getValue())
                .findFirst()
                .orElseThrow(() -> new IncorrectRequestException("Wrong toCurrency name"));
    }
}

package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.client.ExchangeGeneratorClient;
import ru.yandex.practicum.dto.ExchangeDto;
import ru.yandex.practicum.entity.ExchangeEntity;
import ru.yandex.practicum.error.exception.IncorrectRequestException;
import ru.yandex.practicum.mapper.ExchangeEntityMapper;
import ru.yandex.practicum.model.Rate;
import ru.yandex.practicum.repository.ExchangeRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeGeneratorClient exchangeGeneratorClient;
    private final ExchangeRepository exchangeRepository;
    private final ExchangeEntityMapper exchangeEntityMapper;

    public Double exchange(ExchangeDto exchangeDto) {
        log.info("Exchange {}", exchangeDto);
        Double toValue = convertValue(exchangeDto.getFromCurrency(), exchangeDto.getToCurrency(), exchangeDto.getFromValue());
        exchangeDto.setToValue(toValue);
        ExchangeEntity exchangeEntity = exchangeEntityMapper.map(exchangeDto);
        exchangeEntity = exchangeRepository.save(exchangeEntity);
        log.info("Exchange saved: {}", exchangeEntity);
        return toValue;
    }

    private Double convertValue(String fromCurrency, String toCurrency, Double value) {
        List<Rate> rates = exchangeGeneratorClient.getRates();
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

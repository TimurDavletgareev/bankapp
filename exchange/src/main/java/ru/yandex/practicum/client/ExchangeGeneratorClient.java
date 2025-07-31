package ru.yandex.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.RateDto;
import ru.yandex.practicum.model.Rate;
import ru.yandex.practicum.service.ClientService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeGeneratorClient {

    private final ClientService clientService;

    @Value("${resource.alias.exchange-generator}")
    private String resourceAlias;

    public List<Rate> getRates() {
        String endpoint = "/api/rates";
        return clientService.get(resourceAlias, endpoint, RateDto.class).getRates();
    }
}

package ru.yandex.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.TransferDto;
import ru.yandex.practicum.service.ClientService;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeClient {

    private final ClientService clientService;

    @Value("${resource.alias.exchange}")
    private String resourceAlias;

    public Double getToValue(TransferDto transferDto) {
        String endpoint = "/exchange";
        return clientService.postWithBody(resourceAlias, endpoint, transferDto, Double.class);
    }
}

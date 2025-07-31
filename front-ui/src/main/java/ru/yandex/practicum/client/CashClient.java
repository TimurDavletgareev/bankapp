package ru.yandex.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.CashDto;
import ru.yandex.practicum.service.ClientService;

@Service
@Slf4j
@RequiredArgsConstructor
public class CashClient {

    @Value("${resource.alias.cash}")
    private String resourceAlias;

    private final ClientService clientService;

    public void cash(CashDto cashDto) {
        String endpoint = "/cash";
        clientService.postWithBody(resourceAlias, endpoint, cashDto, Void.class);
    }
}

package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.client.AccountClient;
import ru.yandex.practicum.client.ExchangeClient;
import ru.yandex.practicum.dto.TransferDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferService {

    private final ExchangeClient exchangeClient;
    private final AccountClient accountClient;

    public void transfer(TransferDto transferDto) {
        log.info("transfer start: {}", transferDto);
        transferDto.setToValue(exchangeClient.getToValue(transferDto));
        accountClient.updateBalance(transferDto);
    }


}

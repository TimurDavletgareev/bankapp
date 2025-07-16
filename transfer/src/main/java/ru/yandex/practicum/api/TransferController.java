package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.ExchangeDto;
import ru.yandex.practicum.service.TransferService;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    public void transfer(ExchangeDto exchangeDto) {
        transferService.transfer(exchangeDto);
    }
}

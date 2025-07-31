package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.client.AccountClient;
import ru.yandex.practicum.dto.CashDto;

@RestController
@RequiredArgsConstructor
public class CashController {

    private final AccountClient accountClient;

    @PostMapping("/cash")
    public void cash(@RequestBody CashDto cashDto) {
        accountClient.cash(cashDto);
    }
}

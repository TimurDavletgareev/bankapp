package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.client.CashClient;
import ru.yandex.practicum.dto.CashDto;
import ru.yandex.practicum.error.exception.IncorrectRequestException;
import ru.yandex.practicum.model.CashOperation;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CashServiceController {

    private final CashClient cashClient;

    @PostMapping("/user/{login}/cash")
    public String transfer(@PathVariable String login,
                           @RequestParam String currency,
                           @RequestParam Double value,
                           @RequestParam String action,
                           Principal principal) {
        if (!login.equals(principal.getName())) {
            throw new IncorrectRequestException("Not current user login");
        }
        if (value <= 0) {
            throw new IncorrectRequestException("Value must be greater than zero");
        }
        cashClient.cash(CashDto.builder()
                .login(login)
                .currency(currency)
                .value(value)
                .action(CashOperation.valueOf(action))
                .build()
        );
        return "redirect:/main";
    }
}

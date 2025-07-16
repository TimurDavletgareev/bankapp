package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.client.TransferClient;
import ru.yandex.practicum.dto.TransferDto;
import ru.yandex.practicum.error.exception.IncorrectRequestException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class TransferServiceController {

    private final TransferClient transferClient;

    @PostMapping("/user/{login}/transfer")
    public String transfer(@PathVariable String login,
                           @RequestParam String from_currency,
                           @RequestParam String to_currency,
                           @RequestParam Double value,
                           @RequestParam String to_login,
                           Principal principal) {
        if (!login.equals(principal.getName())) {
            throw new IncorrectRequestException("Not current user login");
        }
        if (value <= 0) {
            throw new IncorrectRequestException("Value must be greater than zero");
        }
        transferClient.transfer(TransferDto.builder()
                .login(login)
                .fromCurrency(from_currency)
                .toCurrency(to_currency)
                .fromValue(value)
                .toLogin(to_login)
                .build()
        );
        return "redirect:/main";
    }
}

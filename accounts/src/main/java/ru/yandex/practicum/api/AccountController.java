package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.Currency;
import ru.yandex.practicum.service.AccountService;
import ru.yandex.practicum.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/accounts")
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    @PostMapping("/change-account")
    public boolean create(@RequestParam String email,
                          @RequestParam String accountsString) {
        return accountService.changeAccounts(getUserId(email), accountsString);
    }

    @PostMapping("/update-balance")
    public Double updateBalance(@RequestParam String email,
                                @RequestParam Currency currency,
                                @RequestParam Double amount) {
        return accountService.updateBalance(getUserId(email), currency, amount);
    }

    private Long getUserId(String email) {
        return userService.getUserDtoByEmail(email).getId();
    }
}

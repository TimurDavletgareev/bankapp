package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.AccountDto;
import ru.yandex.practicum.service.AccountService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/accounts")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/save/{userId}")
    public AccountDto addAccount(@RequestBody String currency,
                                 @PathVariable Long userId) {
        return accountService.save(userId, currency);
    }

    @PostMapping("/update/{userId}/{accountId}")
    public Double updateBalance(@PathVariable Long userId,
                                @PathVariable Long accountId,
                                @RequestBody Double amount) {
        return accountService.updateBalance(userId, accountId, amount);
    }

    @DeleteMapping("/delete/{userId}/{accountId}")
    public boolean deleteAccount(@PathVariable Long userId,
                                 @PathVariable Long accountId) {
        return accountService.deleteByAccountId(userId, accountId);
    }
}

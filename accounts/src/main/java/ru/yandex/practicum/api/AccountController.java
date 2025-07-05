package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.AccountDto;
import ru.yandex.practicum.service.AccountService;
import ru.yandex.practicum.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/account")
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    @PostMapping("/save")
    public AccountDto addAccount(@RequestBody String currency, Principal principal) {
        return accountService.save(getCurrentUserId(principal), currency);
    }

    @PostMapping("/update/{accountId}")
    public Double updateBalance(@PathVariable Long accountId,
                                @RequestBody Double amount,
                                Principal principal) {
        return accountService.updateBalance(getCurrentUserId(principal), accountId, amount);
    }

    @DeleteMapping("/delete/{accountId}")
    public boolean deleteAccount(@PathVariable Long accountId,
                                 Principal principal) {
        return accountService.deleteByAccountId(getCurrentUserId(principal), accountId);
    }

    private Long getCurrentUserId(Principal principal) {
        return userService.getCurrentUserDto(principal).getId();
    }
}

package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.CashDto;
import ru.yandex.practicum.dto.TransferDto;
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
    public void updateBalance(@RequestBody TransferDto transferDto) {
        accountService.updateBalance(
                getUserId(transferDto.getLogin()),
                getUserId(transferDto.getToLogin()),
                transferDto
        );
    }

    @PostMapping("/cash")
    public void cash(@RequestBody CashDto cashDto) {
        accountService.cash(getUserId(cashDto.getLogin()), cashDto);
    }

    private Long getUserId(String email) {
        return userService.getUserDtoByEmail(email).getId();
    }
}

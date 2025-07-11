package ru.yandex.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.AccountDto;
import ru.yandex.practicum.entity.Account;
import ru.yandex.practicum.model.Currency;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    public AccountDto map(Account account) {
        return AccountDto.builder()
                .currency(Arrays.stream(Currency.values())
                        .filter(currency -> currency.getTitle().equals(account.getCurrencyTitle()))
                        .findAny()
                        .orElseThrow(() -> new RuntimeException("Currency not found")))
                .value(account.getValue())
                .exists(!account.isDeleted())
                .build();
    }
}

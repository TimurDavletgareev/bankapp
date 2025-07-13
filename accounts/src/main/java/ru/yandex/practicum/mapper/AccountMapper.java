package ru.yandex.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.AccountDto;
import ru.yandex.practicum.entity.Account;
import ru.yandex.practicum.model.Currency;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    public AccountDto map(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .currency(Currency.valueOf(account.getCurrencyName()))
                .value(account.getValue())
                .exists(account.isExists())
                .build();
    }
}

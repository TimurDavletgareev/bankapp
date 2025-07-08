package ru.yandex.practicum.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.AccountDto;
import ru.yandex.practicum.entity.Account;
import ru.yandex.practicum.model.Currency;

import java.util.*;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final ObjectMapper objectMapper;

    public AccountDto map(Account account) {
        return objectMapper.convertValue(account, AccountDto.class);
    }

    public List<AccountDto> map(List<Account> accounts) {
        Map<Currency, AccountDto> allCurrenciesMap = new HashMap<>();
        List<Currency> currencies = Arrays.asList(Currency.values());
        currencies.forEach(currency ->
                allCurrenciesMap.put(currency, AccountDto.builder()
                        .currency(currency)
                        .value(0.0)
                        .exists(false)
                        .build()
                )
        );
        List<AccountDto> listToReturn = new ArrayList<>(3);
        accounts.forEach(account ->
                allCurrenciesMap.keySet().forEach(currency -> {
                    AccountDto accountDto = allCurrenciesMap.get(currency);
                    if (account.getCurrencyTitle().equals(currency.getTitle())) {
                        accountDto.setValue(account.getValue());
                        accountDto.setExists(true);
                    }
                    listToReturn.add(accountDto);
                })
        );
        return listToReturn;
    }
}

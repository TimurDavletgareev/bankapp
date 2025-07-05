package ru.yandex.practicum.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.AccountDto;
import ru.yandex.practicum.entity.Account;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final ObjectMapper objectMapper;

    public AccountDto map(Account account) {
        return objectMapper.convertValue(account, AccountDto.class);
    }
}

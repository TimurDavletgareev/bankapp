package ru.yandex.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.UserFullDto;
import ru.yandex.practicum.service.AccountClientService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountClient {

    private final AccountClientService accountClientService;

    public UserFullDto getCurrentUserDto(String email) {
        log.info("getCurrentUserDto by email{}", email);
        String endpoint = "/user?email=" + email;
        return accountClientService.get(endpoint, UserFullDto.class);
    }

}

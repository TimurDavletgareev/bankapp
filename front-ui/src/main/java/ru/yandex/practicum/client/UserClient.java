package ru.yandex.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ClientService;
import ru.yandex.practicum.dto.UserDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserClient {

    private final ClientService clientService;

    public UserDto getCurrentUserDto(String email) {
        log.info("getCurrentUserDto by email{}", email);
        String endpoint = "/user?email=" + email;
        return clientService.get(endpoint, UserDto.class);
    }

}

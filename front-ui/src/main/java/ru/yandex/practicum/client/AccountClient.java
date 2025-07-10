package ru.yandex.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.AllUsersDto;
import ru.yandex.practicum.dto.CurrencyDto;
import ru.yandex.practicum.dto.UserFullDto;
import ru.yandex.practicum.model.Currency;
import ru.yandex.practicum.service.AccountClientService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountClient {

    private final AccountClientService accountClientService;
    private final PasswordEncoder passwordEncoder;

    public UserFullDto getCurrentUserDto(String email) {
        log.info("getCurrentUserDto by email{}", email);
        String endpoint = "/user?email=" + email;
        return accountClientService.get(endpoint, UserFullDto.class);
    }

    public AllUsersDto getAllUsers() {
        log.info("getAllUsers");
        String endpoint = "/user/all";
        return accountClientService.get(endpoint, AllUsersDto.class);
    }

    public List<Currency> getAllCurrencies() {
        log.info("getAllCurrencies");
        String endpoint = "/currency";
        return accountClientService.get(endpoint, CurrencyDto.class).getCurrency();
    }

    public boolean updateUserPassword(String email, String password) {
        log.info("updateUserPassword");

        String endpoint =
                "/user/update-password"
                        + "?email=" + email
                        + "&password=" + passwordEncoder.encode(password);
        return accountClientService.post(endpoint, boolean.class);
    }
}

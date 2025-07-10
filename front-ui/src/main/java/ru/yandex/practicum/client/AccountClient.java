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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", passwordEncoder.encode(password));
        String endpoint = "/user/update-password" + makeRequestParams(params);
        return accountClientService.post(endpoint, boolean.class);
    }

    public boolean updateUser(String email, String name, String birthDate) {
        log.info("updateUser");
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        if (name != null) params.put("name", name);
        if (birthDate != null) params.put("birthDate", birthDate);
        String endpoint = "/user/update" + makeRequestParams(params);
        return accountClientService.post(endpoint, boolean.class);
    }

    private String makeRequestParams(Map<String, String> params) {
        StringBuilder requestParams = new StringBuilder();
        requestParams.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            requestParams.append(key).append("=").append(value).append("&");
        }
        return requestParams.toString();
    }
}

package ru.yandex.practicum.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AccountClientService {

    @Value("${gateway}")
    private String gateway;

    @Value("${resource.alias.accounts}")
    private String resourceAlias;

    private final TokenService tokenService;

    private RestClient restClient;

    @PostConstruct
    public void init() {
        this.restClient = RestClient.create(gateway);
    }

    public <T> T get(String endpoint, Class<T> type) {
        String accessToken = tokenService.get();
        endpoint = gateway + resourceAlias + endpoint;
        return restClient.get()
                .uri(endpoint)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(type);
    }

    public <T> T post(String endpoint, Class<T> type) {
        String accessToken = tokenService.get();
        endpoint = gateway + resourceAlias + endpoint; //gateway + resourceAlias + endpoint;
        return restClient.post()
                .uri(endpoint)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(type);
    }
}

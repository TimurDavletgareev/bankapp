package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AccountClientService {

    @Value("${resource.server.accounts}")
    private String resource;

    private final TokenService tokenService;

    public <T> T get(String endpoint, Class<T> type) {
        String accessToken = tokenService.get();
        endpoint = resource + endpoint;
        RestClient restClient = RestClient.create(resource);
        return restClient.get()
                .uri(endpoint)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(type);
    }

    public <T> T post(String endpoint, Class<T> type) {
        String accessToken = tokenService.get();
        endpoint = resource + endpoint;
        RestClient restClient = RestClient.create(resource);
        return restClient.post()
                .uri(endpoint)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(type);
    }
}

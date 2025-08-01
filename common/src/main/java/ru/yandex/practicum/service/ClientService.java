package ru.yandex.practicum.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class ClientService {

    private final String gateway;
    private RestClient restClient;

    @PostConstruct
    public void init() {
        this.restClient = RestClient.create(gateway);
    }

    public <T> T get(String resourceAlias, String endpoint, Class<T> returnType) {
        endpoint = gateway + resourceAlias + endpoint;
        return restClient.get()
                .uri(endpoint)
                .retrieve()
                .body(returnType);
    }

    public <T> T post(String resourceAlias, String endpoint, Class<T> type) {
        endpoint = gateway + resourceAlias + endpoint; //gateway + resourceAlias + endpoint;
        return restClient.post()
                .uri(endpoint)
                .retrieve()
                .body(type);
    }

    public <T> T postWithBody(String resourceAlias, String endpoint, Object body, Class<T> returnType) {
        endpoint = gateway + resourceAlias + endpoint;
        return restClient.post()
                .uri(endpoint)
                .body(body)
                .retrieve()
                .body(returnType);
    }
}

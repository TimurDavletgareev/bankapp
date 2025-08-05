package ru.yandex.practicum.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

@Slf4j
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
        log.info("Getting {}", endpoint);
        return restClient.get()
                .uri(endpoint)
                .retrieve()
                .body(returnType);
    }

    public <T> T post(String resourceAlias, String endpoint, Class<T> type) {
        endpoint = gateway + resourceAlias + endpoint;
        log.info("Posting {}", endpoint);
        return restClient.post()
                .uri(endpoint)
                .retrieve()
                .body(type);
    }

    public <T> T postWithBody(String resourceAlias, String endpoint, Object body, Class<T> returnType) {
        endpoint = gateway + resourceAlias + endpoint;
        log.info("Posting with body {}", endpoint);
        return restClient.post()
                .uri(endpoint)
                .body(body)
                .retrieve()
                .body(returnType);
    }
}

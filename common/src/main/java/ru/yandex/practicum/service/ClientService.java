package ru.yandex.practicum.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

@Slf4j
public class ClientService {

    private final String gateway;
    private final TokenService tokenService;
    private RestClient restClient;

    @PostConstruct
    public void init() {
        this.restClient = RestClient.create(gateway);
    }

    public ClientService(String gateway, TokenService tokenService, RestClient.Builder builder) {
        this.gateway = gateway;
        this.tokenService = tokenService;
        this.restClient = builder
                .baseUrl(gateway)
                .build();
    }

    public <T> T get(String resourceAlias, String endpoint, Class<T> returnType) {
        String accessToken = tokenService.get();
        endpoint = gateway + resourceAlias + endpoint;
        log.info("Getting {}", endpoint);
        return restClient.get()
                .uri(endpoint)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(returnType);
    }

    public <T> T post(String resourceAlias, String endpoint, Class<T> returnType) {
        log.info("Post {}", endpoint);
        String accessToken = tokenService.get();
        endpoint = gateway + resourceAlias + endpoint;
        return restClient.post()
                .uri(endpoint)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(returnType);
    }

    public <T> T postWithBody(String resourceAlias, String endpoint, Object body, Class<T> returnType) {
        String accessToken = tokenService.get();
        endpoint = gateway + resourceAlias + endpoint;
        log.info("Posting with body {}", endpoint);
        return restClient.post()
                .uri(endpoint)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .retrieve()
                .body(returnType);
    }
}

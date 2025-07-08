package ru.yandex.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ClientService {

    @Value("${spring.security.oauth2.client.registration.bankapp-front.client-id}")
    private String client_id;

    @Value("${resource.server.accounts}")
    private String resource;

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public <T> T get(String endpoint, Class<T> type) {
        OAuth2AuthorizedClient client = authorizedClientManager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId(client_id)
                .principal("system")
                .build()
        );
        assert client != null;
        String accessToken = client.getAccessToken().getTokenValue();
        endpoint = resource + endpoint;
        RestClient restClient = RestClient.create(resource);
        return restClient.get()
                .uri(endpoint)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(type);
    }
}

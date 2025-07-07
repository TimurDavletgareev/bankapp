package ru.yandex.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserClient {

    @Value("${spring.security.oauth2.client.registration.bankapp-front.client-id}")
    private String client_id;

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Value("${resource.server.accounts}")
    private String resource;

    public Object getAccounts() {

        OAuth2AuthorizedClient client = authorizedClientManager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId(client_id)
                .principal("system")
                .build()
        );

        assert client != null;
        String accessToken = client.getAccessToken().getTokenValue();

        RestClient restClient = RestClient.create("http://localhost:8010");
        return restClient.get()
                .uri("/user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // Подставляем токен доступа в заголовок Authorization
                .retrieve()
                .body(Object.class);

    }

}

package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@RequiredArgsConstructor
public class TokenService {

    private final String client_id;

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public String get() {
        OAuth2AuthorizedClient client = authorizedClientManager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId(client_id)
                .principal("system")
                .build()
        );
        assert client != null;
        return client.getAccessToken().getTokenValue();
    }
}

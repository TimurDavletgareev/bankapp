package ru.yandex.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsClient implements UserDetailsService {

    @Value("${spring.security.oauth2.client.registration.bankapp-front.client-id}")
    private String client_id;

    @Value("${resource.server.accounts}")
    private String resource;

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OAuth2AuthorizedClient client = authorizedClientManager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId(client_id)
                .principal("system")
                .build()
        );

        assert client != null;
        String accessToken = client.getAccessToken().getTokenValue();
        /*RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        String endpoint = resource + "/users/?email=" + username;
        return restTemplate.getForObject(endpoint, UserDetails.class, username);*/

        RestClient restClient = RestClient.create(resource);
        String endpoint = resource + "/user-details?email=" + username;
        return restClient.get()
                .uri(endpoint)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(UserDetails.class);
    }
}

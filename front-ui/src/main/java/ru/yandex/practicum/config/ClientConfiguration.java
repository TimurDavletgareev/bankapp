package ru.yandex.practicum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import ru.yandex.practicum.service.ClientService;
import ru.yandex.practicum.service.TokenService;

@Configuration
public class ClientConfiguration {

    @Value("${spring.security.oauth2.client.registration.bankapp-front.client-id}")
    private String client_id;

    @Autowired
    private OAuth2AuthorizedClientManager authorizedClientManager;

    @Value("${gateway}")
    private String gateway;

    @Bean
    TokenService tokenService() {
        return new TokenService(client_id, authorizedClientManager);
    }

    @Bean
    ClientService clientService() {
        return new ClientService(gateway, tokenService());
    }
}

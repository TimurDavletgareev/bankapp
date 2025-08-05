package ru.yandex.practicum.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.service.ClientService;

@Configuration
public class ClientConfiguration {

    @Value("${gateway}")
    private String gateway;

    @Bean
    ClientService clientService() {
        return new ClientService(gateway);
    }
}

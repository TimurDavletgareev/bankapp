package ru.yandex.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.NotificationDto;
import ru.yandex.practicum.service.ClientService;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationClient {

    @Value("${resource.alias.notification}")
    private String resourceAlias;

    private final ClientService clientService;

    public void notify(NotificationDto notificationDto) {
        log.info("Sending notification: {}", notificationDto);
        String endpoint = "/notify";
        clientService.postWithBody(resourceAlias, endpoint, notificationDto, Void.class);
    }
}

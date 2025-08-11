package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {

    private String email;
    private String subject;
    private String message;
}

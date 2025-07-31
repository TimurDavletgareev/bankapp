package ru.yandex.practicum.error;

import lombok.Getter;

@Getter
public class ErrorResponse {

    String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

}

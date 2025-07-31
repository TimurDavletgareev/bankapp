package ru.yandex.practicum.model;

import lombok.Getter;

@Getter
public enum Currency {

    RUB("Ruble"),
    USD("Dollar"),
    CNY("Yen");

    private final String title;

    Currency(String title) {
        this.title = title;
    }
}

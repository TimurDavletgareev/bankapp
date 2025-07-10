package ru.yandex.practicum.model;

import lombok.Getter;

@Getter
public enum Currency {

    RUB("Russian ruble"),
    USD("American dollar"),
    CNY("Chinese yen");

    private final String title;

    Currency(String title) {
        this.title = title;
    }
}

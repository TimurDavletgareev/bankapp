package ru.yandex.practicum.model;

import lombok.Getter;

@Getter
public enum Currency {

    RUB("RUB", "Russian ruble"),
    USD("USD", "American dollar"),
    CNY("CNY", "Chinese yen");

    private final String title;
    private final String name;

    Currency(String title, String name) {
        this.title = title;
        this.name = name;
    }
}

package ru.yandex.practicum.dto;

import lombok.Data;

@Data
public class ExchangeDto {

    private Long id;
    private String login;
    private String fromCurrency;
    private String toCurrency;
    private Double value;
    private String toLogin;
}

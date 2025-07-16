package ru.yandex.practicum.dto;

import lombok.Data;

@Data
public class ExchangeDto {

    private Long id;
    private String login;
    private String fromCurrency;
    private Double fromValue;
    private String toLogin;
    private String toCurrency;
    private Double toValue;

}

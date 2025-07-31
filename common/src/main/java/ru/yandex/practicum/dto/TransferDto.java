package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferDto {

    private Long id;
    private String login;
    private String fromCurrency;
    private Double fromValue;
    private String toLogin;
    private String toCurrency;
    private Double toValue;

}

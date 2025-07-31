package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.model.CashOperation;

@Data
@Builder
public class CashDto {

    private String login;
    private String currency;
    private Double value;
    private CashOperation action;
}

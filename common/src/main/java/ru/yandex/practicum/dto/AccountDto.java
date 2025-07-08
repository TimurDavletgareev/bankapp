package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.model.Currency;

@Data
@Builder
public class AccountDto {

    private Currency currency;
    private Double value;
    private Boolean exists;
}

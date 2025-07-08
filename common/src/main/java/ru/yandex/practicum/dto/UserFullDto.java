package ru.yandex.practicum.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserFullDto {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private List<AccountDto> accounts;
}

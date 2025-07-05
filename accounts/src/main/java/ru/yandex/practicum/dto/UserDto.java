package ru.yandex.practicum.dto;

import lombok.Data;
import ru.yandex.practicum.entity.Account;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private List<Account> accounts;
}

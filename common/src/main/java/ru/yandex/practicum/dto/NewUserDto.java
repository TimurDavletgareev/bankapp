package ru.yandex.practicum.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;

@Data
public class NewUserDto {

    @NonNull
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private LocalDate birthDate;
    @NonNull
    @ToString.Exclude
    private String password;
}

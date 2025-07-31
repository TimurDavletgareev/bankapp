package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Builder
public class NewUserDto {

    @NonNull
    private String email;
    @NonNull
    private String name;
    @NonNull
    private LocalDate birthDate;
    @NonNull
    @ToString.Exclude
    private String password;
}

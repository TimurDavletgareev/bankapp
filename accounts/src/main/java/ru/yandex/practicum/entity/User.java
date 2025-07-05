package ru.yandex.practicum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Table(name = "users")
@Getter
@Setter
@ToString
public class User {

    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column
    @NonNull
    private String email;

    @Column
    @NonNull
    private String firstName;

    @Column
    @NonNull
    private String lastName;

    @Column
    @NonNull
    private LocalDate birthDate;

    @JsonIgnore
    @ToString.Exclude
    private String password;

    @Column
    private boolean isDeleted;
}

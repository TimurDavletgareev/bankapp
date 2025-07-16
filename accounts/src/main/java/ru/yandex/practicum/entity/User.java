package ru.yandex.practicum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(schema = "accounts_schema", name = "users")
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
    private String name;

    @Column
    @NonNull
    private LocalDate birthDate;

    @JsonIgnore
    @ToString.Exclude
    private String password;

    @Column
    private boolean isDeleted;
}

package ru.yandex.practicum.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(schema = "accounts_schema", name = "users")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column
    @ToString.Exclude
    private String password;

    @Column
    private boolean isDeleted;
}

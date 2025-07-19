package ru.yandex.practicum.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "accounts_schema", name = "accounts")
@Getter
@Setter
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column
    @NonNull
    private Long userId;

    @Column
    @NonNull
    private Double value;

    @Column
    @NonNull
    private String currencyName;

    @Column
    private boolean isExists;
}

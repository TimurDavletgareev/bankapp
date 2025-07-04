package ru.yandex.practicum.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

@Table(name = "accounts")
@Getter
@Setter
@ToString
public class Account {

    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column
    @NonNull
    private Double balance;

    @Column
    @NonNull
    private String currency;
}

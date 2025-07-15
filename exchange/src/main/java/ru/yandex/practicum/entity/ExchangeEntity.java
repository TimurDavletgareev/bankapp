package ru.yandex.practicum.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "exchange")
@Getter
@Setter
@ToString
public class ExchangeEntity {

    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column
    @NonNull
    private String login;

    @Column
    @NonNull
    private String fromCurrency;

    @Column
    @NonNull
    private Double fromValue;

    @Column
    @NonNull
    private String toCurrency;

    @Column
    @NonNull
    private Double toValue;

    @Column
    @NonNull
    private String toLogin;
}

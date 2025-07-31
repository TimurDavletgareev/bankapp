package ru.yandex.practicum.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "exchange_schema", name = "exchange")
@Getter
@Setter
@ToString
public class ExchangeEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

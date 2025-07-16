package ru.yandex.practicum.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(schema = "accounts_schema", name = "roles")
@Getter
@Setter
@ToString
public class Role {

    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column
    @NonNull
    private Long userId;

    @Column
    private String roleName;
}

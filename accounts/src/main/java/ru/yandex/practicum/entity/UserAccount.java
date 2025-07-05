package ru.yandex.practicum.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

@Table(name = "user_accounts")
@Getter
@Setter
@ToString
@Builder
public class UserAccount {

    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column
    @NonNull
    private Long userId;

    @Column
    @NonNull
    private Long accountId;

    @Column
    boolean isDeleted;
}

package ru.yandex.practicum.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(schema = "accounts_schema", name = "user_accounts")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

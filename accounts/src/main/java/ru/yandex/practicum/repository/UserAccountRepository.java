package ru.yandex.practicum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entity.UserAccount;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
}

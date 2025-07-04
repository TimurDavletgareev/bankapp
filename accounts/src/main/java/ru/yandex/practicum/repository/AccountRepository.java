package ru.yandex.practicum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}

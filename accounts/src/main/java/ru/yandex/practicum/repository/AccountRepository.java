package ru.yandex.practicum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entity.Account;

import java.util.List;


@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findByIdIn(List<Long> ids);
}

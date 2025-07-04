package ru.yandex.practicum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}

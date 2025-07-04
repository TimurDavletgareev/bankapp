package ru.yandex.practicum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}

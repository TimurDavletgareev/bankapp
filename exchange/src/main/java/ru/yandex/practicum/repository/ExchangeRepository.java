package ru.yandex.practicum.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.practicum.entity.ExchangeEntity;

public interface ExchangeRepository extends CrudRepository<ExchangeEntity, Long> {
}

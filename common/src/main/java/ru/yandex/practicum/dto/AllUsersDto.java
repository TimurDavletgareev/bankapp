package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AllUsersDto {
    private List<UserShortDto> users;
}

package ru.yandex.practicum.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.NewUserDto;
import ru.yandex.practicum.dto.UserDto;
import ru.yandex.practicum.entity.User;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ObjectMapper objectMapper;

    public UserDto map(User user) {
        return objectMapper.convertValue(user, UserDto.class);
    }

    public User map(NewUserDto newUserDto) {
        return objectMapper.convertValue(newUserDto, User.class);
    }
}

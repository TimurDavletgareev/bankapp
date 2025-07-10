package ru.yandex.practicum.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.NewUserDto;
import ru.yandex.practicum.dto.UserFullDto;
import ru.yandex.practicum.dto.UserShortDto;
import ru.yandex.practicum.entity.User;
import ru.yandex.practicum.util.NullChecker;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ObjectMapper objectMapper;

    public UserFullDto mapToFullDto(User user) {
        return objectMapper.convertValue(user, UserFullDto.class);
    }

    public User mapToFullDto(NewUserDto newUserDto) {
        return objectMapper.convertValue(newUserDto, User.class);
    }

    public UserShortDto mapToShortDto(User user) {
        return UserShortDto.builder()
                .login(user.getEmail())
                .name(user.getName())
                .build();
    }

    public UserFullDto update(UserFullDto userFullDtoWithUpdates, UserFullDto userFullDtoToUpdate) {
        NullChecker.setIfNotNull(userFullDtoToUpdate::setName, userFullDtoWithUpdates.getName());
        NullChecker.setIfNotNull(userFullDtoToUpdate::setBirthDate, userFullDtoWithUpdates.getBirthDate());
        return userFullDtoToUpdate;
    }
}

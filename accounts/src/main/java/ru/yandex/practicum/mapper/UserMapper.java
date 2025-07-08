package ru.yandex.practicum.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public UserFullDto mapToFullDto(User user) {
        return objectMapper.convertValue(user, UserFullDto.class);
    }

    public User mapToFullDto(NewUserDto newUserDto) {
        User user = objectMapper.convertValue(newUserDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    public UserShortDto mapToShortDto(User user) {
        return UserShortDto.builder()
                .login(user.getEmail())
                .name(user.getFirstName() + " " + user.getLastName())
                .build();
    }

    public UserFullDto update(UserFullDto userFullDtoWithUpdates, UserFullDto userFullDtoToUpdate) {
        NullChecker.setIfNotNull(userFullDtoToUpdate::setFirstName, userFullDtoWithUpdates.getFirstName());
        NullChecker.setIfNotNull(userFullDtoToUpdate::setLastName, userFullDtoWithUpdates.getLastName());
        NullChecker.setIfNotNull(userFullDtoToUpdate::setBirthDate, userFullDtoWithUpdates.getBirthDate());
        return userFullDtoToUpdate;
    }

    public String mapPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

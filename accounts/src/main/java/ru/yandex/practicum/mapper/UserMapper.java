package ru.yandex.practicum.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.NewUserDto;
import ru.yandex.practicum.dto.UserDto;
import ru.yandex.practicum.entity.User;
import ru.yandex.practicum.util.NullChecker;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto map(User user) {
        return objectMapper.convertValue(user, UserDto.class);
    }

    public User map(NewUserDto newUserDto) {
        User user = objectMapper.convertValue(newUserDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    public UserDto update(UserDto userDtoWithUpdates, UserDto userDtoToUpdate) {
        NullChecker.setIfNotNull(userDtoToUpdate::setFirstName, userDtoWithUpdates.getFirstName());
        NullChecker.setIfNotNull(userDtoToUpdate::setLastName, userDtoWithUpdates.getLastName());
        NullChecker.setIfNotNull(userDtoToUpdate::setBirthDate, userDtoWithUpdates.getBirthDate());
        NullChecker.setIfNotNull(userDtoToUpdate::setPassword,
                passwordEncoder.encode(userDtoWithUpdates.getPassword()));
        return userDtoToUpdate;
    }
}

package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.NewUserDto;
import ru.yandex.practicum.dto.UserDto;
import ru.yandex.practicum.entity.User;
import ru.yandex.practicum.mapper.UserMapper;
import ru.yandex.practicum.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername '{}'", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.warn("User not found by email '{}'", email);
            return null;
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                roleService.getRoles(user.getId())
        );
        log.info("UserDetails returned: {}", userDetails);
        return userDetails;
    }

    public UserDto getByUserId(Long userId) {
        log.info("getByUserId '{}'", userId);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.warn("User not found by id '{}'", userId);
            return null;
        }
        UserDto userDto = userMapper.map(user);
        userDto.setAccounts(accountService.getByUserId(userId));
        log.info("UserDto returned: {}", userDto);
        return userDto;
    }

    public UserDto saveUser(NewUserDto newUserDto) {
        log.info("saveUser '{}'", newUserDto);
        if (newUserDto == null) {
            log.warn("UserDto is null");
            return null;
        }
        User user = userMapper.map(newUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        UserDto userDtoToReturn = userMapper.map(savedUser);
        log.info("UserDto saved: {}", userDtoToReturn);
        return userDtoToReturn;
    }
}

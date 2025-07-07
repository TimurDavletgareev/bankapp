package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.NewUserDto;
import ru.yandex.practicum.dto.UserDto;
import ru.yandex.practicum.entity.User;
import ru.yandex.practicum.error.exception.NotFoundException;
import ru.yandex.practicum.mapper.UserMapper;
import ru.yandex.practicum.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder; //TODO: for test only

    public UserDetails loadUserByUsername(String userName) {
        log.info("loadUserByUsername '{}'", userName);
        User user = getUserByEmail(userName);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                //кодируем, т.к. пользователь создаётся в бд на старте
                passwordEncoder.encode((user.getPassword())), //TODO: for test only
                roleService.getRoles(user.getId())
        );
        log.info("UserDetails returned: {}", userDetails);
        return userDetails;
    }

    public UserDto getUserDtoById(Long userId) {
        log.info("get current user");
        User user = getUserById(userId);
        UserDto userDto = userMapper.map(user);
        userDto.setAccounts(accountService.getByUserId(user.getId()));
        log.info("current user: {}", userDto);
        return userDto;
    }

    @Transactional
    public UserDto saveUser(NewUserDto newUserDto) {
        log.info("saveUser '{}'", newUserDto);
        if (newUserDto == null) {
            throw new IllegalArgumentException("UserDto is null");
        }
        if (userRepository.findByEmail(newUserDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = userMapper.map(newUserDto);
        user.setDeleted(false);
        User savedUser = userRepository.save(user);
        UserDto userDtoToReturn = userMapper.map(savedUser);
        log.info("UserDto saved: {}", userDtoToReturn);
        return userDtoToReturn;
    }

    @Transactional
    public UserDto updateUser(UserDto userDto, Long userId) {
        log.info("updateUser '{}'", userDto);
        if (userDto == null) {
            log.warn("UpdateUserDto is null");
            return null;
        }
        UserDto userToUpdate = userMapper.map(getUserById(userId));
        UserDto updatedUser = userMapper.update(userDto, userToUpdate);
        log.info("UserDto updated: {}", updatedUser);
        return updatedUser;
    }

    @Transactional
    public boolean updatePassword(String password, Long userId) {
        log.info("updatePassword");
        User user = getUserById(userId);
        user.setPassword(userMapper.mapPassword(password));
        userRepository.save(user);
        log.info("Password updated");
        return true;
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        log.info("deleteUser");
        User user = getUserById(userId);
        user.setDeleted(true);
        userRepository.save(user);
        accountService.deleteAllByUserId(user.getId());
        log.info("user deleted");
        return true;
    }

    private User getUserById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found by id=" + userId));
    }

    private User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException("Cannot find user by email: " + email);
        }
        return user;
    }
}

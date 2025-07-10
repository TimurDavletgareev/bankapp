package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.entity.User;
import ru.yandex.practicum.error.exception.NotFoundException;
import ru.yandex.practicum.mapper.UserMapper;
import ru.yandex.practicum.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final AccountService accountService;

    public UserDetailsDto loadUserByUsername(String userName) {
        log.info("loadUserByUsername '{}'", userName);
        User user = getUserByEmail(userName);
        UserDetailsDto userDetails = new UserDetailsDto(
                user.getEmail(),
                user.getPassword(),
                roleService.getRoles(user.getId())
        );
        log.info("UserDetails returned: {}", userDetails);
        return userDetails;
    }

    public UserFullDto getUserDtoByEmail(String email) {
        log.info("get current user");
        User user = getUserByEmail(email);
        UserFullDto userFullDto = userMapper.mapToFullDto(user);
        userFullDto.setAccounts(accountService.getByUserId(user.getId()));
        log.info("current user: {}", userFullDto);
        return userFullDto;
    }

    public AllUsersDto getAllUsers() {
        log.info("get all users");
        List<UserShortDto> listToReturn = new ArrayList<>();
        userRepository.findAll().forEach(user -> listToReturn.add(userMapper.mapToShortDto(user)));
        log.info("getAllUsers size: {}", listToReturn.size());
        return AllUsersDto.builder()
                .users(listToReturn)
                .build();
    }

    @Transactional
    public UserFullDto saveUser(NewUserDto newUserDto) {
        log.info("saveUser '{}'", newUserDto);
        if (newUserDto == null) {
            throw new IllegalArgumentException("UserFullDto is null");
        }
        if (userRepository.findByEmail(newUserDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = userMapper.mapToFullDto(newUserDto);
        user.setDeleted(false);
        User savedUser = userRepository.save(user);
        UserFullDto userFullDtoToReturn = userMapper.mapToFullDto(savedUser);
        log.info("UserFullDto saved: {}", userFullDtoToReturn);
        return userFullDtoToReturn;
    }

    @Transactional
    public UserFullDto updateUser(UserFullDto userFullDto, String email) {
        log.info("updateUser '{}'", userFullDto);
        if (userFullDto == null) {
            log.warn("UpdateUserDto is null");
            return null;
        }
        UserFullDto userToUpdate = userMapper.mapToFullDto(getUserByEmail(email));
        UserFullDto updatedUser = userMapper.update(userFullDto, userToUpdate);
        log.info("UserFullDto updated: {}", updatedUser);
        return updatedUser;
    }

    @Transactional
    public boolean updatePassword(String email, String password) {
        log.info("updatePassword");
        User user = getUserByEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        log.info("Password updated");
        return true;
    }

    @Transactional
    public boolean deleteUser(String email) {
        log.info("deleteUser");
        User user = getUserByEmail(email);
        user.setDeleted(true);
        userRepository.save(user);
        accountService.deleteAllByUserId(user.getId());
        log.info("user deleted");
        return true;
    }

    private User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException("Cannot find user by email: " + email);
        }
        return user;
    }
}

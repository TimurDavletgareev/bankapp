package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.NewUserDto;
import ru.yandex.practicum.dto.UserDto;
import ru.yandex.practicum.entity.User;
import ru.yandex.practicum.mapper.UserMapper;
import ru.yandex.practicum.repository.UserRepository;

import java.security.Principal;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername '{}'", email);
        User user = getUserByEmail(email);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                roleService.getRoles(user.getId())
        );
        log.info("UserDetails returned: {}", userDetails);
        return userDetails;
    }

    public UserDto getCurrentUserDto(Principal principal) {
        log.info("get current user");
        User user = getCurrentUser(principal);
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
        if (isEmailExists(newUserDto.getEmail())) {
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
    public UserDto updateUser(UserDto userDto, Principal principal) {
        log.info("updateUser '{}'", userDto);
        if (userDto == null) {
            log.warn("UpdateUserDto is null");
            return null;
        }
        UserDto userToUpdate = userMapper.map(getCurrentUser(principal));
        UserDto updatedUser = userMapper.update(userDto, userToUpdate);
        log.info("UserDto updated: {}", updatedUser);
        return updatedUser;
    }

    @Transactional
    public boolean updatePassword(String password, Principal principal) {
        log.info("updatePassword");
        User user = getCurrentUser(principal);
        user.setPassword(userMapper.mapPassword(password));
        userRepository.save(user);
        log.info("Password updated");
        return true;
    }

    @Transactional
    public boolean deleteUser(Principal principal) {
        log.info("deleteUser");
        User user = getCurrentUser(principal);
        user.setDeleted(true);
        userRepository.save(user);
        accountService.deleteAllByUserId(user.getId());
        log.info("user deleted");
        return true;
    }

    private User getCurrentUser(Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("Principal cannot be null");
        }
        String email = principal.getName();
        return getUserByEmail(email);
    }

    private User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null || user.isDeleted()) {
            throw new UsernameNotFoundException("Cannot find user by email: " + email);
        }
        return user;
    }

    private boolean isEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}

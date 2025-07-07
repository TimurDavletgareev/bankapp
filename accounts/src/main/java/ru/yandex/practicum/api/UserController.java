package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.NewUserDto;
import ru.yandex.practicum.dto.UserDto;
import ru.yandex.practicum.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public UserDto addUser(@RequestBody NewUserDto newUserDto) {
        return userService.saveUser(newUserDto);
    }

    @PostMapping("/update/{userId}")
    public UserDto updateUser(@RequestBody UserDto UserDto,
                              @PathVariable Long userId) {
        return userService.updateUser(UserDto, userId);
    }

    @PostMapping("/update-password/{userId}")
    public boolean updateUser(@RequestBody String password,
                              @PathVariable Long userId) {
        return userService.updatePassword(password, userId);
    }

    @DeleteMapping("/delete/{userId}")
    public boolean deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}

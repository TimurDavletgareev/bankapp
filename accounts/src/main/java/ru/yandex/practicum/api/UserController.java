package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.dto.NewUserDto;
import ru.yandex.practicum.dto.UserDto;
import ru.yandex.practicum.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserDto getCurrentUser(Principal principal) {
        return userService.getCurrentUserDto(principal);
    }

    @PostMapping("/save")
    public UserDto addUser(@RequestBody NewUserDto newUserDto) {
        return userService.saveUser(newUserDto);
    }

    @PostMapping("/update")
    public UserDto updateUser(@RequestBody UserDto UserDto, Principal principal) {
        return userService.updateUser(UserDto, principal);
    }
}

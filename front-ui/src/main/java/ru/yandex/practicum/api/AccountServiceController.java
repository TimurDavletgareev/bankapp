package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.client.UserClient;
import ru.yandex.practicum.dto.NewUserDto;
import ru.yandex.practicum.dto.UserDto;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class AccountServiceController {

    private final UserClient userClient;
/*
    @GetMapping
    public UserDto getCurrentUser(Principal principal) {
        return userClient.getCurrentUserDto(principal);
    }

    @PostMapping("/save")
    public UserDto addUser(@RequestBody NewUserDto newUserDto) {
        return userClient.saveUser(newUserDto);
    }

    @PostMapping("/update")
    public UserDto updateUser(@RequestBody UserDto UserDto, Principal principal) {
        return userClient.updateUser(UserDto, principal);
    }

    @PostMapping("/update-password")
    public boolean updateUser(@RequestBody String password, Principal principal) {
        return userClient.updatePassword(password, principal);
    }

    @DeleteMapping("/delete")
    public boolean deleteUser(Principal principal) {
        return userClient.deleteUser(principal);
    }*/
}

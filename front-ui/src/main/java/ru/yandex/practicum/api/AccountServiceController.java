package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.client.AccountClient;
import ru.yandex.practicum.dto.UserDto;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
@Slf4j
public class AccountServiceController {

    private final AccountClient accountClient;

    @GetMapping
    public String getMainPage(Principal principal, Model model) {
        log.info("getMainPage for {}", principal.getName());
        UserDto userDto = accountClient.getCurrentUserDto(principal.getName());
        model.addAttribute("login", userDto.getEmail());
        model.addAttribute("name", userDto.getFirstName() + " " + userDto.getLastName());
        model.addAttribute("birthdate", userDto.getBirthDate());
        model.addAttribute("accounts", userDto.getAccounts());
        return "main";
    }
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

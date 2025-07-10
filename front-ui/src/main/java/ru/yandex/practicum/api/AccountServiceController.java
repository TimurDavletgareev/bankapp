package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.client.AccountClient;
import ru.yandex.practicum.dto.UserFullDto;

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
        UserFullDto userFullDto = accountClient.getCurrentUserDto(principal.getName());
        model.addAttribute("login", userFullDto.getEmail());
        model.addAttribute("name", userFullDto.getFirstName() + " " + userFullDto.getLastName());
        model.addAttribute("birthdate", userFullDto.getBirthDate());
        model.addAttribute("accounts", userFullDto.getAccounts());
        model.addAttribute("users", accountClient.getAllUsers().getUsers());
        model.addAttribute("currency", accountClient.getAllCurrencies());
        return "main";
    }
/*
    @GetMapping
    public UserFullDto getCurrentUser(Principal principal) {
        return userClient.getCurrentUserDto(principal);
    }

    @PostMapping("/save")
    public UserFullDto addUser(@RequestBody NewUserDto newUserDto) {
        return userClient.saveUser(newUserDto);
    }

    @PostMapping("/update")
    public UserFullDto updateUser(@RequestBody UserFullDto UserFullDto, Principal principal) {
        return userClient.updateUser(UserFullDto, principal);
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
